package com.lioncorp.common.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

import com.lioncorp.common.util.Constants;
import com.lioncorp.common.util.SerializerUtil;

public abstract class AbstractRedisCache implements ICacheProtocol {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRedisCache.class);
    public static final int CACHE_RETRY_TIMES = 3;
    
    protected static JedisPoolConfig jedisPoolConfig;

    protected static ShardedJedisPoolWrapper shardedJedisPoolWrapper;

    protected static ShardedJedisPool pool;
    
    public static void initConfig(){
        jedisPoolConfig=new JedisPoolConfig();
//        jedisPoolConfig.setMaxActive(Constants.REDIS.redis_pool_maxActive);
        jedisPoolConfig.setMaxIdle(Constants.REDIS.redis_pool_maxIdle);
//        jedisPoolConfig.setMaxWait(Constants.REDIS.redis_pool_maxWait);
        jedisPoolConfig.setTestOnBorrow(Constants.REDIS.redis_pool_testOnBorrow);
        jedisPoolConfig.setTestOnReturn(Constants.REDIS.redis_pool_testOnReturn);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(Constants.REDIS.redis_pool_minEvictableIdleTimeMillis);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Constants.REDIS.redis_pool_timeBetweenEvictionRunsMillis);
    }

    public ShardedJedisPool getPool() {
        return pool;
    }


    /**
     * transaction cache operation
     * 
     * @param key
     * @throws java.io.IOException
     */
    public void transaction(String key, CacheOperationFactory factory) throws IOException {
        boolean isFailed = false;
        ShardedJedis jedis = pool.getResource();
        try {
            Jedis shard = jedis.getShard(key);
            shard.watch(SafeEncoder.encode(key));
            Transaction t = shard.multi();
            factory.setJedis(shard);
            factory.cache();
            t.exec();
        } catch (JedisException e) {
            isFailed = true;
            throw e;
        } finally {
            if (isFailed) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    protected abstract class CacheOperationFactory {
        private Jedis jedis;

        public void setJedis(Jedis jedis) {
            this.jedis = jedis;
        }

        public Jedis getJedis() {
            return this.jedis;
        }

        public abstract void cache() throws JedisException, IOException;
    }
    
    protected abstract class CountOperationFactory<T> {
        int count;
        boolean isGet;
        String key;
        String[] keys;

        public CountOperationFactory(int count, String key, boolean isGet) {
            this.count = count;
            this.key = key;
            this.isGet = isGet;
        }

        public CountOperationFactory(int count, String[] keys, boolean isGet) {
            this.count = count;
            this.keys = keys;
            this.isGet = isGet;
        }

        public T operate() throws JedisException, IOException {
            if (count <= 0) {
                throw new JedisException("cache operate count less than 1");
            }

            while (count > 0) {
                try {
                    return jedisTemplate();
                } catch (JedisException e) {
                    logger.error("cache CountOperation exception, count:{}, {}",count, e.getMessage());
                    e.printStackTrace();
                    count--;
                }
            }
            if (!isGet) {
              //  去掉操作3次失败后，删除缓存的操作.
              //  delete();
            }
            throw new JedisException("cache CountOperation exception count=" + count);
        }

        private T jedisTemplate() throws JedisException, IOException {
            ShardedJedis jedis = null;
            boolean isFailed = false;
            try {
                jedis = pool.getResource();
                return cacheOperate(jedis);
            } catch (JedisException e) {
                if(!(e.getCause() instanceof JedisDataException)) {
                    isFailed = true;
                    if(jedis != null && key != null) {
                        JedisShardInfo shardInfo = jedis.getShardInfo(key);
                        logger.error("JedisException@{}:{}", shardInfo.getHost(), shardInfo.getPort());
                    }
                }

                throw e;
            } finally {
                if (jedis != null) {
                    if (isFailed) {
                        pool.returnBrokenResource(jedis);
                    } else {
                        pool.returnResource(jedis);
                    }
                }
            }
        }

        @SuppressWarnings("unused")
		private void delete() throws IOException {
            if (StringUtils.isBlank(key)) {
                return;
            }
            ShardedJedis jedis = null;
            boolean isFailed = false;
            try {
                jedis = pool.getResource();
                jedis.getShard(key).del(SafeEncoder.encode(key));
            } catch (JedisException e) {
                isFailed = true;
                logger.error("delete all data of key:{}, {} ",key, e.getMessage());
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    if (isFailed) {
                        pool.returnBrokenResource(jedis);
                    } else {
                        pool.returnResource(jedis);
                    }
                }
            }
        }

        public abstract T cacheOperate(ShardedJedis jedis) throws JedisException, IOException;
    }

    @Override
    public List<String> setByte(final String[] keys, final Object[] os, final int expireTime){
        if(keys.length != os.length || keys.length == 0) {
            return null;
        }
        try {
            return new CountOperationFactory<List<String>>(CACHE_RETRY_TIMES, keys, false) {
                @SuppressWarnings("rawtypes")
    			public List<String> cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    List<String> ts = new ArrayList<String>(keys.length);
                    List<Response<String>> responses = new ArrayList<Response<String>>(keys.length);
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(int i = 0; i < keys.length; i++) {
                        responses.add(pipeline.setex(SafeEncoder.encode(keys[i]),expireTime,SerializerUtil.write(os[i])));
                    }
                    pipeline.sync();
                    for(Response response : responses) {
                        ts.add((String) response.get());
                    }
                    return ts;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when set a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            logger.error("exception occur when set a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<String> set(final String[] keys, final Object[] os){
        if(keys.length != os.length || keys.length == 0) {
            return null;
        }
        try {
            return new CountOperationFactory<List<String>>(CACHE_RETRY_TIMES, keys, false) {
                @SuppressWarnings("rawtypes")
    			public List<String> cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    List<String> ts = new ArrayList<String>(keys.length);
                    List<Response<String>> responses = new ArrayList<Response<String>>(keys.length);
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(int i = 0; i < keys.length; i++) {
                    	logger.info("testset debug, key:{}", keys[i]);
                        responses.add(pipeline.set(keys[i], os[i].toString()));
                    }
                    pipeline.sync();
                    for(Response response : responses) {
                        ts.add((String) response.get());
                    }
                    return ts;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when set a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            logger.error("exception occur when set a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public <T> List<T> getByte(final String[] keys){
        try {
            return new CountOperationFactory<List<T>>(CACHE_RETRY_TIMES, keys, true) {
                @SuppressWarnings({ "rawtypes", "unchecked"})
				public List<T> cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                	List<T> ts = new ArrayList<T>(keys.length);
                    List<Response<byte[]>> responses = new ArrayList<Response<byte[]>>(keys.length);
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(String key : keys) {
                        responses.add(pipeline.getByte(SafeEncoder.encode(key)));
                    }
                    pipeline.sync();
                    for(Response response : responses) {
                        T t = (T) SerializerUtil.read((byte[]) response.get());
                        ts.add(t);
                    }
                    return ts;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when get a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            logger.error("exception occur when get a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public <T> List<T> get(final String[] keys){
        try {
            return new CountOperationFactory<List<T>>(CACHE_RETRY_TIMES, keys, true) {
                @SuppressWarnings({ "rawtypes", "unchecked"})
				public List<T> cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                	List<T> ts = new ArrayList<T>(keys.length);
                    List<Response<String>> responses = new ArrayList<Response<String>>(keys.length);
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(String key : keys) {
                        responses.add(pipeline.get(key));
                    }
                    pipeline.sync();
                    for(Response response : responses) {
                        T t = (T) (String) response.get();
                        ts.add(t);
                    }
                    return ts;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when get a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            logger.error("exception occur when get a object, {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean expire(String key, final int seconds){
        try {
            return new CountOperationFactory<Boolean>(CACHE_RETRY_TIMES, key, true) {
                public Boolean cacheOperate(ShardedJedis jedis) throws IOException {
                    Long expired = jedis.expire(key, seconds);
                    return expired != null;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling expire, {}", e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            logger.error("exception occur when calling expire, {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Long> expires(String[] keys, final int seconds){
        if(keys.length == 0) {
            return null;
        }
        try {
            return new CountOperationFactory<List<Long>>(CACHE_RETRY_TIMES, keys, true) {
                @SuppressWarnings("rawtypes")
				public List<Long> cacheOperate(ShardedJedis jedis) throws IOException {
                    List<Response<Long>> responses = new ArrayList<Response<Long>>(keys.length);
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(int i = 0; i < keys.length; i++) {
                        responses.add(pipeline.expire(keys[i], seconds));
                    }
                    pipeline.sync();
                    List<Long> res = new ArrayList<Long>(keys.length);
                    for(Response response : responses) {
                        res.add((Long)response.get());
                    }
                    return res;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling expire, {}", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            logger.error("exception occur when calling expire, {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<Long> expires2(String[] keys, final int[] seconds){
        if(keys.length == 0) {
            return null;
        }
        try {
            return new CountOperationFactory<List<Long>>(CACHE_RETRY_TIMES, keys, true) {
                @SuppressWarnings("rawtypes")
				public List<Long> cacheOperate(ShardedJedis jedis) throws IOException {
                    List<Response<Long>> responses = new ArrayList<Response<Long>>(keys.length);
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(int i = 0; i < keys.length; i++) {
                        responses.add(pipeline.expire(keys[i], seconds[i]));
                    }
                    pipeline.sync();
                    List<Long> res = new ArrayList<Long>(keys.length);
                    for(Response response : responses) {
                        res.add((Long)response.get());
                    }
                    return res;
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling expire, {}", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            logger.error("exception occur when calling expire, {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
	public boolean setex(String[] keys, Object[] os, int expireTime) {
    	if(keys.length != os.length || keys.length == 0) {
            return false;
        }
        try {
            return new CountOperationFactory<Boolean>(CACHE_RETRY_TIMES, keys, false) {
    			public Boolean cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    ExtendedShardedJedisPipeline pipeline = new ExtendedShardedJedisPipeline(jedis);
                    for(int i = 0; i < keys.length; i++) {
                    	logger.info("test debug, key:{}", keys[i]);
                        pipeline.setex(SafeEncoder.encode(keys[i]), expireTime, SerializerUtil.write(os[i]));
                    }
                    pipeline.sync();
                    return true;
                }
                
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when set a object, {}", e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            logger.error("exception occur when set a object, {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
		
	}
    
    @Override
	public Long lRem(String key, String value) {
    	try {
            return new CountOperationFactory<Long> (CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                   return jedis.lrem(key, 0, value);
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling lRem, {}", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("exception occur when calling lRem, {}", e.getMessage());
            e.printStackTrace();
        }
    	return 0l;
	}


	@Override
	public Set<String> zrange(String key, long start, long end) {
		try {
            return new CountOperationFactory<Set<String>> (CACHE_RETRY_TIMES, key, true) {
                public Set<String> cacheOperate(ShardedJedis jedis) throws IOException {
                   return jedis.zrange(key, start, end);
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling zrange, {}", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("exception occur when calling zrange, {}", e.getMessage());
            e.printStackTrace();
        }
    	return Collections.emptySet();
	}

	@Override
	public Long lpush(String key, List<String> list) {
		try {
            return new CountOperationFactory<Long> (CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                   return jedis.lpush(key, list.toArray(new String[0]));
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling lpush, {}", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("exception occur when calling lpush, {}", e.getMessage());
            e.printStackTrace();
        }
    	return 0l;
	}

	@Override
	public Long sadd(String key, List<String> list) {
		try {
            return new CountOperationFactory<Long> (CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                   return jedis.sadd(key, list.toArray(new String[0]));
                }
            }.operate();
        } catch (JedisException e) {
            logger.error("exception occur when calling sadd, {}", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("exception occur when calling sadd, {}", e.getMessage());
            e.printStackTrace();
        }
    	return 0l;
	}
	
	/***
     * cache lock,默认10s
     * @param key
     */
    public boolean lock(String key) {
        return lock(key, 10);
    }

    /**
     * Cache lock.
     * 
     * @param key
     * @param seconds 任务间隔时间控制
     * @return
     */
    public boolean lock(String key, int seconds) {
        ShardedJedis shardedJedis = null;
        boolean isFailed = false;
        try {
            shardedJedis = getPool().getResource();
            
            Jedis jedis = shardedJedis.getShard(key);
            
            if(jedis.exists(key)) {
                return false;
            }
            jedis.watch(key);
            Transaction t = jedis.multi();
            Response<Long> response = t.setnx(key, "0");
            t.expire(key, seconds);
            List<Object> list = t.exec();

            return list != null && response.get() > 0;
        } catch (JedisException e) {
            isFailed = true;
            logger.error("lock {}, {}",key, e.getMessage());
            e.printStackTrace();
        } finally {
            if(shardedJedis!=null){
                if (isFailed) {
                    getPool().returnBrokenResource(shardedJedis);
                } else {
                    getPool().returnResource(shardedJedis);
                }
            }
        }
        return false;
    }
    
    /**
     * release lock
     * <P/>谨慎使用 如果任务时间比lock字段存在时间久  释放的可能是其他任务的lock
     * <P/>一般情况下可不使用
     * 
     * @param key
     * @return
     */
    public boolean releaseLock(String key) {
        // return this.getPool().getResource().getShard(key).del(key)>0;
        ShardedJedis jedis = null;
        boolean isFailed = false;
        try {
            jedis = this.getPool().getResource();
            return jedis.del(key) > 0;
        } catch (JedisException e) {
            isFailed = true;
            logger.error("releaseLock key {}, {}",key, e.getMessage());
            e.printStackTrace();
        } finally {
            if(jedis!=null){
                if (isFailed) {
                    this.getPool().returnBrokenResource(jedis);
                } else {
                    this.getPool().returnResource(jedis);
                }
            }
        }
        return false;
    }
}
