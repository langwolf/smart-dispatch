package com.lioncorp.dispatch.service;


import java.util.Collections;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.lioncorp.dispatch.dao.db.mapper.ReceiveMapper;
import com.lioncorp.dispatch.model.Receive;

@Service
public class DbStoreService extends BaseService implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(DbStoreService.class);

	@Resource
	private ReceiveMapper receiveMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	public void insertInfo(String docid, String test) {
		Receive receive = new Receive();	
		receiveMapper.saveOrUpdate(Collections.singletonList(receive));
	}
}
