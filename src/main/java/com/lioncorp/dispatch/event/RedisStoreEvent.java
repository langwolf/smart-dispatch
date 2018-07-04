package com.lioncorp.dispatch.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.alibaba.fastjson.JSONObject;

public class RedisStoreEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JSONObject> result = null;
	private LOCAL_PATH localPathName = null;
	
	public RedisStoreEvent(List<JSONObject> result, LOCAL_PATH localPathName) {
		super(result);		
		this.result = result;
		this.localPathName = localPathName;
	}

	public List<JSONObject> getResult() {
		return result;
	}

	public LOCAL_PATH getLocalPathName() {
		return localPathName;
	}

	@Override
	public Object getSource() {
		return super.getSource();
	}


	public enum LOCAL_PATH {
		TEST1("local.path1"),
		TEST2("local.path2");
		
		private String name;
		private LOCAL_PATH(String name){
			this.name = name;
		}
		public String getName() {
			return name;
		}
		
	}
}
