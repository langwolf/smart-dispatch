package com.lioncorp.common.redis;

import redis.clients.jedis.JedisCluster;

public class RedisClusterContext {

    private JedisCluster jedisCluster;

    private JedisCluster standyJedisCluster;

    // cluster redis exptime
    private int maxExpireTime = 360 * 24 * 3600; // seconds

    private RedisMonitor monitor;

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public JedisCluster getStandyJedisCluster() {
        return standyJedisCluster;
    }

    public void setStandyJedisCluster(JedisCluster standyJedisCluster) {
        this.standyJedisCluster = standyJedisCluster;
    }

    public int getMaxExpireTime() {
        return maxExpireTime;
    }

    public void setMaxExpireTime(int maxExpireTime) {
        this.maxExpireTime = maxExpireTime;
    }

    public RedisMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(RedisMonitor monitor) {
        this.monitor = monitor;
    }
}
