package com.lioncorp.dispatch.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lioncorp.common.redis.RedisClusterCache;

public class RedisClusterDao extends RedisClusterCache {
    private static final Logger logger = LoggerFactory
            .getLogger(RedisClusterDao.class);

    private static RedisClusterDao instance;

    private RedisClusterDao() {
        super();
    }

    public synchronized static RedisClusterDao getInstance() {
        if (instance == null) {
            instance = new RedisClusterDao();
        }
        return instance;
    }
    
}
