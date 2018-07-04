package com.lioncorp.dispatch.service;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.common.thrift.iface.SEARCH_TYPE;

/**
 * @author bjssgong
 *
 */
public interface AssembleByQueue {
		
	public boolean isLegalData(JSONObject obj);
	
	public SEARCH_TYPE getProject();
}
