package com.lioncorp.common.redis;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;

import com.google.common.base.Splitter;
import com.lioncorp.common.util.PropertyUtil;

/**
 * persistenceJedisPool
 */
public abstract class RedisClusterCache {
	
	private static Logger logger = LoggerFactory.getLogger(RedisClusterCache.class);
	private static Splitter splitter = Splitter.on(';');
	private final static String JEDIS_TEST1_ADDRESS = PropertyUtil.get("jedis.cluster.address.test1");
    private final static String JEDIS_TEST2_ADDRESS = PropertyUtil.get("jedis.cluster.address.test2");
    
    static{
    	setUpTest1();
    	setUpForTest2();
    }
    
    public static RedisClusterFactory clusterFactoryTest1;    
    public static RedisClusterFactory clusterFactoryTest2;    

    public static void setUpTest1() {
    	clusterFactoryTest1 = new RedisClusterFactory();

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();       
        for(String address : splitter.splitToList(JEDIS_TEST1_ADDRESS)){
			String[] tmp = address.split(":");
			logger.info("rank redis cluster ip:{}, port:{}", tmp[0], tmp[1]);
			jedisClusterNodes.add(new HostAndPort(tmp[0], Integer.parseInt(tmp[1])));
		}

        clusterFactoryTest1.setJedisClusterNodes(jedisClusterNodes);

    }
    
    public static void setUpForTest2() {
    	clusterFactoryTest2 = new RedisClusterFactory();

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        
        for(String address : splitter.splitToList(JEDIS_TEST2_ADDRESS)){
			String[] tmp = address.split(":");
			logger.info("redis cluster ip:{}, port:{}", tmp[0], tmp[1]);
			jedisClusterNodes.add(new HostAndPort(tmp[0], Integer.parseInt(tmp[1])));
		}

        clusterFactoryTest2.setJedisClusterNodes(jedisClusterNodes);

    } 
}
