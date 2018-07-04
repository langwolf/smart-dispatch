package com.lioncorp.dispatch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDataService extends AbstractDeleteService {

	private static Logger logger = LoggerFactory.getLogger(DeleteDataService.class);
	

	@Autowired
	public DeleteDataService(DbStoreService dbDataService, CacheStoreService storeCacheService) {
		super.dbDataService = dbDataService;
		super.storeCacheService = storeCacheService;
	}


	@Override
	protected void deleteBjrecExplorData(String docid, String skipType) {
//		
	}


}
