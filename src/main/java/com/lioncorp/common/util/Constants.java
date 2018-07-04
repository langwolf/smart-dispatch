package com.lioncorp.common.util;


public class Constants {
	
    public static final class THRIFT_SERVER{
    	public final static String TOPIC_ADDRESS = PropertyUtil.get("topic.address");
    }

    public static final class REDIS {
        public final static int redis_pool_maxActive = Integer.parseInt(PropertyUtil.get("redis.pool.maxActive"));
        public final static int redis_pool_maxIdle = Integer.parseInt(PropertyUtil.get("redis.pool.maxIdle"));
        public final static int redis_pool_maxWait = Integer.parseInt(PropertyUtil.get("redis.pool.maxWait"));
        public final static boolean redis_pool_testOnBorrow = Boolean.parseBoolean(PropertyUtil.get("redis.pool.testOnBorrow"));
        public final static boolean redis_pool_testOnReturn = Boolean.parseBoolean(PropertyUtil.get("redis.pool.testOnReturn"));
        public final static int redis_pool_minEvictableIdleTimeMillis = Integer.parseInt(PropertyUtil.get("redis.pool.minEvictableIdleTimeMillis"));
        public final static int redis_pool_timeBetweenEvictionRunsMillis = Integer.parseInt(PropertyUtil.get("redis.pool.timeBetweenEvictionRunsMillis"));
        
        public final static String jedis_pool_address = PropertyUtil.get("jedis.pool.address");
        public final static String jedis_pool_password = PropertyUtil.get("jedis.pool.password");

    }
 	
 	public static final class OUT_PARAM {
 		public final static String DOCID = "docid";
 		public final static String OP = "op";
 	}

 	public static final class IN_PARAM {
 		public final static String DOCID = "docid";
 	}
 	
 	public static final class ENV {
 		public final static String NAME = PropertyUtil.get("env");
 		public final static String ONLINE = "online";
 		public final static String TEST = "test";
 	}

}
