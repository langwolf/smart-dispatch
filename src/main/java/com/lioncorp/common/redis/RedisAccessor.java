package com.lioncorp.common.redis;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

public class RedisAccessor extends RedisAccessorBase {

	private static final Logger logger = LoggerFactory.getLogger(RedisAccessor.class);
	
    public RedisAccessor(String prefix, RedisClusterContext context) {
        super(prefix, context);
    }

    public String get(final String key, final String defaultValue) {
        return new ReadCommandTemplate<String>(key, defaultValue) {
            @Override
            public String read(JedisCluster client, String redisKey) {
                String value = client.get(redisKey);
                if (value == null) {
                    return defaultValue;
                } else {
                    return value;
                }
            }
        }.run();
    }

    public boolean setex(final String key, final String value, final int expireSeconds) {
        return new WriteCommandTemplateBoolean(key, expireSeconds, value) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                client.setex(redisKey, seconds, value);
                return true;
            }
        }.run();
    }
    
    public boolean set(final String key, final String value) {
        return new WriteCommandTemplateBoolean(key, 0, value) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                client.set(redisKey, value);
                return true;
            }
        }.run();
    }

    public Boolean exists(final String key) {
        return new ReadCommandTemplate<Boolean>(key, false) {
            @Override
            public Boolean read(JedisCluster client, String redisKey) {
            	return client.exists(redisKey);
            }
        }.run();
    }
    
    public boolean lpush(final String key, final int expireSeconds, final String... value) {
        return new WriteCommandTemplateBoolean(key, expireSeconds, value) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int expireSeconds) {
                client.lpush(redisKey, value);
                client.expire(redisKey, expireSeconds);
                return true;
            }
        }.run();
    }
    
    public boolean delete(final String key) {
        return new DeleteCommandTemplate(key) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                return client.del(redisKey) > 0;
            }
        }.run();
    }

    public long incrby(String key, final int increment, final long defaultValue, final int expireSeconds) {
        return new WriteCommandTemplate<Long>(key, expireSeconds, defaultValue, StringUtils.EMPTY) {
            @Override
            public Long write(JedisCluster client, String redisKey, int seconds) {
                long value = client.incrBy(redisKey, increment);
                client.expire(redisKey, seconds);
                return value;
            }
        }.run();
    }

    public String hget(final String key, final String field, final String defaultValue) {
        return new ReadCommandTemplate<String>(key, defaultValue) {
            @Override
            public String read(JedisCluster client, String redisKey) {
                String value = client.hget(redisKey, field);
                if (value == null) {
                    return defaultValue;
                } else {
                    return value;
                }
            }
        }.run();
    }
    
    public boolean hset(final String key, final String field, final String value, final int expireSeconds) {
        return new WriteCommandTemplateBoolean(key, expireSeconds, value) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                client.hset(redisKey, field, value);
                client.expire(redisKey, seconds);
                return true;
            }
        }.run();
    }

    public boolean hmset(final String key, final Map<String, String> hash, final int expireSeconds) {
        return new WriteCommandTemplateBoolean(key,  expireSeconds, StringUtils.EMPTY) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                client.hmset(redisKey, hash);
                client.expire(redisKey, seconds);
                return true;
            }
        }.run();
    }

    public boolean hdel(final String key, final String field) {
        return new DeleteCommandTemplate(key) {
            @Override
            public Boolean write(JedisCluster client, String redisKey, int seconds) {
                return client.hdel(redisKey, field) > 0;
            }
        }.run();
    }

    public long hincrby(final String key, final String field, final int increment, final long defaultValue, final int expireSeconds) {
        return new WriteCommandTemplate<Long>(key, expireSeconds, defaultValue, String.valueOf(increment)) {
            @Override
            public Long write(JedisCluster client, String redisKey, int seconds) {
                long value = client.hincrBy(redisKey, field, increment);
                client.expire(redisKey, seconds);
                return value;
            }
        }.run();
    }

    public Set<String> smembers(final String key) {
        return new ReadCommandTemplate<Set<String>>(key, Collections.emptySet()) {
            @Override
            public Set<String> read(JedisCluster client, String redisKey) {
                return client.smembers(redisKey);
            }
        }.run();
    }

    public long sadd(final String key, final int expireSeconds, final String... members) {
        return new WriteCommandTemplate<Long>(key, expireSeconds, -1L, StringUtils.EMPTY) {
            @Override
            public Long write(JedisCluster client, String redisKey, int seconds) {
                long ret = client.sadd(redisKey, members);
                client.expire(redisKey, seconds);
                return ret;
            }
        }.run();
    }
    
    public Set<String> zrange(final String key, final long start, final long end) {
        return new ReadCommandTemplate<Set<String>>(key, Collections.emptySet()) {
            @Override
            public Set<String> read(JedisCluster client, String redisKey) {
                return client.zrange(redisKey, start, end);
            }
        }.run();
    }

}
