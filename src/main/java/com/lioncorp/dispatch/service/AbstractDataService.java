package com.lioncorp.dispatch.service;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.lioncorp.common.thrift.iface.ExtractResultInfo;
import com.lioncorp.common.thrift.iface.QualityResponseInfo;
import com.lioncorp.common.thrift.iface.SEARCH_TYPE;

/**
 * @author bjssgong
 *
 */

public abstract class AbstractDataService extends BaseAssembleByQueue {

	private Logger logger = LoggerFactory.getLogger(AbstractDataService.class);
	protected final static int PAGE_SIZE = 10;
	
	protected FilterDataService filterDataService;
	
	protected static Gson gson = new Gson();
	protected int threadNum = 8;
	protected int maxQueueSize = 2000;
	protected ExecutorService executor;
	
	@Override
	public List<JSONObject> getData() {
		return Collections.emptyList();
	}

	private CompletableFuture<List<ExtractResultInfo>> createTopicData(List<JSONObject> paramList) {
		return CompletableFuture.supplyAsync(() -> { 
			return filterDataService.topicDataStore(paramList);
		}, executor)
		.handle((result, ex) -> {
			if (null == result || result.isEmpty()) {
				logger.error("create data error:{}", ex);
			} 
			return result;
		});
	}
	
	private CompletableFuture<List<QualityResponseInfo>> createQualityData(SEARCH_TYPE project, List<JSONObject> paramList) {
		return CompletableFuture.supplyAsync(() -> {  
			return filterDataService.qualityDataStore(project, paramList);		
		}, executor)
		.handle((result, ex) -> {
			if (null == result || result.isEmpty()) {
				logger.warn("create data error:{}", ex);
			} 
			return result;
		});
	}
	
	public List<JSONObject> syncAssembleData(BaseAssembleByQueue assembleByQueue) {
		return assembleData(assembleByQueue, true);
	}
	
	public List<JSONObject> asyncAssembleData(BaseAssembleByQueue assembleByQueue) {
		return assembleData(assembleByQueue, false);
	}
	
	public List<JSONObject> assembleData(BaseAssembleByQueue assembleByQueue, boolean isSync) {
		if(null == assembleByQueue)
			return Collections.emptyList();
		List<JSONObject> allList = assembleByQueue.getData();
		if(null == allList || allList.isEmpty())
			return Collections.emptyList();
		// filter
		allList = assembleByQueue.pretreat(allList);
		if (null == allList || allList.isEmpty())
			return Collections.emptyList();

		List<JSONObject> resultList = Lists.newArrayList();
		int pageNum = 1;
		int tmp = allList.size() / PAGE_SIZE;
		int pageCount = (allList.size() % PAGE_SIZE == 0) ? tmp : (tmp + 1);
		List<JSONObject> pageList = null;
		int start = 0, end = 0;
		List<ExtractResultInfo> topiclist = null;
		List<QualityResponseInfo> qualitylist = null;
		CompletableFuture<List<ExtractResultInfo>> topicFuture = null;
		CompletableFuture<List<QualityResponseInfo>> qualityFuture = null;
		while (pageNum <= pageCount) {
			start = end;
			end = PAGE_SIZE * pageNum > allList.size() ? allList.size()
					: PAGE_SIZE * pageNum;
			final List<JSONObject> paramList = allList.subList(start, end);
			
			if(null == paramList || paramList.isEmpty())
				continue;
			if (isSync) {
				topiclist = filterDataService.topicDataStore(paramList);
				qualitylist = filterDataService.qualityDataStore(assembleByQueue.getProject(), 
						paramList);
				pageList = assemble(paramList, topiclist, qualitylist);
			} else {
				topicFuture = createTopicData(paramList);
				qualityFuture = createQualityData(assembleByQueue.getProject(),
						paramList);

				CompletableFuture<List<JSONObject>> resultFuture = topicFuture
						.thenCombine(qualityFuture,
								(t, q) -> assemble(paramList, t, q));
				try {
					pageList = resultFuture.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			if(null != pageList && !pageList.isEmpty()){
				resultList.addAll(pageList);
			}
			pageNum++;
		}
		return resultList;
	}
		
	protected  abstract List<JSONObject> assemble(List<JSONObject> pageList,
			List<ExtractResultInfo> extPageList, 
			List<QualityResponseInfo> qualityPageList);
	
	
}
