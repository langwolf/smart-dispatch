package com.lioncorp.dispatch.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lioncorp.common.redis.RedisCache;

public class RedisDao extends RedisCache {
    private static final Logger logger = LoggerFactory
            .getLogger(RedisDao.class);

    private static RedisDao instance;

    private RedisDao() {
        super();
    }

    public synchronized static RedisDao getInstance() {
        if (instance == null) {
            instance = new RedisDao();
        }
        return instance;
    }
}
