package com.lioncorp.common.redis;

import java.util.List;
import java.util.Set;

public interface ICacheProtocol {
    public List<String> setByte(final String[] keys, final Object[] os, final int expireTime);
    
    public <T> List<T> getByte(final String[] keys);

    public boolean expire(String key, int seconds);

    public List<Long> expires(String[] keys, final int seconds);
    
    public List<Long> expires2(String[] keys, final int[] seconds);

    public Long lRem(String key, String value);
    
    public boolean setex(final String[] keys, final Object[] os, final int expireTime);
        
    public Long lpush(final String key, List<String> list);
    
    public Long sadd(final String key, List<String> list);
    
    public <T> List<T> get(final String[] keys);

    public List<String> set(final String[] keys, final Object[] os);
    
    public Set<String> zrange(final String key, final long start, final long end);
}
