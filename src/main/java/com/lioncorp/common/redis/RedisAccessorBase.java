package com.lioncorp.common.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisCluster;

public class RedisAccessorBase {

    private String prefix;  // redis key's prefix

    private RedisClusterContext redisClusterContext;

    protected abstract class ReadCommandTemplate<T> {

        private String key;

        private T defaultValue;

        public ReadCommandTemplate(String key, T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public abstract T read(JedisCluster client, String redisKey);

        public T run() {
            String redisKey = prefix + key;

//            logRead(prefix, key);
            T value = doRead(getCluster(), redisKey);
//            if (value == null) {
//                value = doRead(getStandbyCluster(), redisKey);
//            }
            return value == null ? defaultValue : value;
        }

        private T doRead(JedisCluster client, String redisKey) {
            if (client != null) {
                try {
                    return read(client, redisKey);
                } catch (Exception e) {
                    logReadError(prefix, key, e);
                }
            }
            return null;
        }
    }

    protected abstract class WriteCommandTemplate<T> {

        private String key;

        private String value;
        
        private String[] values;

        private int expireSeconds;

        private T defaultValue;

        public WriteCommandTemplate(String key, int expireSeconds, T defaultValue, String... values) {
            this.key = key;
            this.values = values;
            this.expireSeconds = expireSeconds;
            this.defaultValue = defaultValue;
        }


        public abstract T write(JedisCluster client, String redisKey, int seconds);
        
        public T run() {
            String redisKey = prefix + key;
            int seconds = adjustExpireTime(expireSeconds);

//            logWrite(prefix, key, this.value);
            T value = doWrite(getCluster(), redisKey, seconds);
//            doWrite(getStandbyCluster(), redisKey, seconds);
            return value == null ? defaultValue : value;
        }

        private T doWrite(JedisCluster client, String redisKey, int seconds) {
            if (client != null) {
                try {
                    return write(client, redisKey, seconds);
                } catch (Exception e) {
                    logWriteError(prefix, key, e);
                }
            }
            return null;
        }
    }

    protected abstract class WriteCommandTemplateBoolean extends WriteCommandTemplate<Boolean> {       
        public WriteCommandTemplateBoolean(String key, int expireSeconds, String... values) {
            super(key, expireSeconds, false, values);
        }
    }

    protected abstract class ReadCommandTemplateString extends ReadCommandTemplate<String> {       
        public ReadCommandTemplateString(String key, String... values) {
            super(key, "");
        }
    }
    
    protected abstract class DeleteCommandTemplate extends WriteCommandTemplateBoolean {
        public DeleteCommandTemplate(String key) {
            super(key, 0, StringUtils.EMPTY);
        }
    }

    protected RedisAccessorBase(String prefix, RedisClusterContext redisClusterContext) {
        this.prefix = prefix;
        this.redisClusterContext = redisClusterContext;
    }

    protected void logRead(String prefix, String key) {
        RedisMonitor monitor = redisClusterContext.getMonitor();
        if (monitor != null) {
            monitor.onRead(prefix, key);
        }
    }

    protected void logWrite(String prefix, String key, String value) {
        RedisMonitor monitor = redisClusterContext.getMonitor();
        if (monitor != null) {
            monitor.onWrite(prefix, key, value);
        }
    }

    protected void logReadError(String prefix, String key, Exception e) {
        RedisMonitor monitor = redisClusterContext.getMonitor();
        if (monitor != null) {
            monitor.handleReadError(prefix, key, e);
        }
    }

    protected void logWriteError(String prefix, String key, Exception e) {
        RedisMonitor monitor = redisClusterContext.getMonitor();
        if (monitor != null) {
            monitor.handleWriteError(prefix, key, e);
        }
    }

    protected int adjustExpireTime(int seconds) {
        return Math.min(seconds, redisClusterContext.getMaxExpireTime());
    }

    protected JedisCluster getCluster() {
        return redisClusterContext.getJedisCluster();
    }

    private JedisCluster getStandbyCluster() {
        return redisClusterContext.getStandyJedisCluster();
    }
}
