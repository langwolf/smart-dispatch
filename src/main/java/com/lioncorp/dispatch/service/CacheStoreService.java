package com.lioncorp.dispatch.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.lioncorp.dispatch.dao.redis.RedisClusterDao;
import com.lioncorp.dispatch.dao.redis.RedisDao;

@Service
public class CacheStoreService extends BaseService implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(CacheStoreService.class);

	private RedisClusterDao redisClusterDao = null;
	private RedisDao redisDao = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		redisClusterDao = RedisClusterDao.getInstance();
		redisDao = RedisDao.getInstance();
	}	
	
	public void insertForTest(String key, String value) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(value))				
			return;
//		redisClusterDao
//		redisDao
	}	


}
