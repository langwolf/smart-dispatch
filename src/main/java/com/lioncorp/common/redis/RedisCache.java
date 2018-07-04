package com.lioncorp.common.redis;

import com.lioncorp.common.util.Constants;

/**
 * persistenceJedisPool
 */
public abstract class RedisCache extends AbstractRedisCache {
    static{
        init();
    }
    
    public static void init() {
        initConfig();
        shardedJedisPoolWrapper=new ShardedJedisPoolWrapper(jedisPoolConfig,Constants.REDIS.jedis_pool_address,Constants.REDIS.jedis_pool_password);
        pool = shardedJedisPoolWrapper.getJedisPool();
    }
}
