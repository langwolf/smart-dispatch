package com.lioncorp.common.redis;


public interface RedisMonitor {

    void onRead(String prefix, String key);

    void onWrite(String prefix, String key, String value);

    void handleReadError(String prefix, String key, Exception e);

    void handleWriteError(String prefix, String key, Exception e);

}
