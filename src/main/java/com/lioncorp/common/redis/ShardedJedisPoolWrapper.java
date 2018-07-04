package com.lioncorp.common.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class ShardedJedisPoolWrapper {
    
    private static final Logger logger = LoggerFactory.getLogger(ShardedJedisPoolWrapper.class);
    
    public static final int REDIS_TIMEOUT = 1000;
    
    private ShardedJedisPool jedisPool;
    
    public ShardedJedisPoolWrapper(JedisPoolConfig config, String address) {
        logger.info("Redis started on address: " + address);
        jedisPool = new ShardedJedisPool(config, buildShardInfos(address, ""));
    }
    
    public ShardedJedisPoolWrapper(JedisPoolConfig config, String address, String password) {
        logger.info("Redis started on address: " + address);
        jedisPool = new ShardedJedisPool(config, buildShardInfos(address, password));
    }
    
    public ShardedJedisPool getJedisPool() {
        return this.jedisPool;
    }
 
    private static List<JedisShardInfo> buildShardInfos(String address, String password) {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        for(String addr : address.split(";")) {
            String[] parts = addr.split(":");
            String host = parts[0];
            int port = Integer.parseInt(parts[1]);
            JedisShardInfo info = new JedisShardInfo(host, port, REDIS_TIMEOUT);
            if (!StringUtils.isEmpty(password)) {
                info.setPassword(password);
            }
            shards.add(info);
        }
        return shards;
    }
}
