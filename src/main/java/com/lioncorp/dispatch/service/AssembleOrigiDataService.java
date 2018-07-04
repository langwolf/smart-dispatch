package com.lioncorp.dispatch.service;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lioncorp.common.thrift.iface.ExtractResultInfo;
import com.lioncorp.common.thrift.iface.QualityResponseInfo;

@Service
public class AssembleOrigiDataService extends AbstractDataService implements Closeable {
	private static Logger logger = LoggerFactory
			.getLogger(AssembleOrigiDataService.class);

	
	private ExecutorService executor = new ThreadPoolExecutor(threadNum,
	        threadNum, 0L, TimeUnit.MILLISECONDS,
	        new ArrayBlockingQueue<Runnable>(maxQueueSize),
	        new ThreadPoolExecutor.CallerRunsPolicy());
	
	@Autowired
	public AssembleOrigiDataService(FilterDataService filterDataService) {
		super.executor = executor;
		super.filterDataService = filterDataService;
	}


	@Override
	public List<JSONObject> assemble(List<JSONObject> pageList,
			List<ExtractResultInfo> extPageList, 
			List<QualityResponseInfo> qualityPageList) {
		if (null == pageList || pageList.isEmpty())
			return Collections.emptyList();
		List<JSONObject> resultList = Lists.newArrayList();
		for (JSONObject t : pageList) {
			JSONObject result = new JSONObject();
			result.putAll(t);
			if (null != extPageList && !extPageList.isEmpty()) {
				for (ExtractResultInfo tmp : extPageList) {
					// somthing
				}
			}
			if (null != qualityPageList && !qualityPageList.isEmpty()){
				for (QualityResponseInfo qua : qualityPageList) {
					// somthing
				}
			}
			
			
			resultList.add(result);
		}
		return resultList;
	}
	
	@Override
	public void close() throws IOException {		
		executor.shutdown();
		try {
		    if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		    	executor.shutdownNow();
		    } 
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}
	}
	
}
