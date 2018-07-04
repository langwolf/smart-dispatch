package com.lioncorp.common.redis.pipeline;

import redis.clients.jedis.Protocol;
import redis.clients.jedis.Response;

public class RedisCommand<T> {
    Protocol.Command op;
    String key;
    String value;
    int expireTime;
    Response<T> response;

    public RedisCommand(Protocol.Command op, String key, String value, int expireTime) {
        this.op = op;
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
        this.response = null;
    }
}