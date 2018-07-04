package com.lioncorp.dispatch.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lioncorp.common.util.Constants;
import com.lioncorp.dispatch.service.AbstractDeleteService.DELETE_ITEM;

@Service
public class AssembleDataService extends BaseService {
	private static Logger logger = LoggerFactory.getLogger(AssembleDataService.class);
	@Resource
	private DeleteDataService deleteDataService;

	public List<JSONObject> assembleData(List<JSONObject> list) {
		if(null == list || list.isEmpty())
			return Collections.emptyList();
		List<JSONObject> resultList = Lists.newArrayList();
		for(JSONObject tmp : list) {
			JSONObject obj = assembleData(tmp);
			if(null != obj && !obj.isEmpty()){
				resultList.add(obj);
			}
		}
		return resultList;
	}
	
	public JSONObject assembleData(JSONObject obj) {
		if(null == obj || obj.isEmpty())
			return new JSONObject();
		if("".equals(DEL)){
			deleteDataService.deleteBjrecData("", "", DELETE_ITEM.TEST);
			return new JSONObject();
		} else {
			return headlineData(obj);
		}
	}

	public JSONObject headlineData(JSONObject obj) {
		JSONObject result = new JSONObject();
		result.put(Constants.OUT_PARAM.OP, ADD);
		return result;
	}
}
