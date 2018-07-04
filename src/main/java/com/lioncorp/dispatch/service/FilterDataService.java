package com.lioncorp.dispatch.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.common.thrift.iface.ExtractResultInfo;
import com.lioncorp.common.thrift.iface.QualityResponseInfo;
import com.lioncorp.common.thrift.iface.SEARCH_TYPE;

@Service
public class FilterDataService extends BaseService {
	
	private static Logger logger = LoggerFactory.getLogger(FilterDataService.class);

	@Resource
	private TopicDataService topicDataService;
	@Resource
	private CacheStoreService storeCacheService;
	
	public List<ExtractResultInfo> topicDataStore(List<JSONObject> allList) {
		if(null == allList || allList.isEmpty())
			return Collections.emptyList();
		
		return topicDataService.parserNews(allList);
	}
	
	public List<QualityResponseInfo> qualityDataStore(SEARCH_TYPE project,
			List<JSONObject> allList) {
		if (null == allList || allList.isEmpty())
			return Collections.emptyList();		
		return null;
	}
	
}
