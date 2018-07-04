package com.lioncorp.dispatch.service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.lioncorp.common.thrift.iface.SEARCH_TYPE;

/**
 * @author bjssgong
 *
 */
public abstract class BaseAssembleByQueue implements AssembleByQueue {
	
	private static Logger logger = LoggerFactory.getLogger(BaseAssembleByQueue.class);
	private static Gson gson = new Gson();
	
	public abstract List<JSONObject> getData();

	@Override
	public boolean isLegalData(JSONObject obj) {
		return !(StringUtils.isBlank(MapUtils.getString(obj, "")));
	}

	@Override
	public SEARCH_TYPE getProject() {
		return null;
	}

	public  List<JSONObject> pretreat(List<JSONObject> allList) {
		if(null == allList || allList.isEmpty())
			return Collections.emptyList();
		List<JSONObject> resultList = Lists.newArrayList(allList);
		Iterator<JSONObject> it = resultList.iterator();
		while(it.hasNext()){
			JSONObject obj = it.next();
			if(!isLegalData(obj)){
				it.remove();
			}
		}
		return resultList;
	}
}
