package com.lioncorp.dispatch.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.alibaba.fastjson.JSONObject;

public class DbStoreEvent extends ApplicationEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JSONObject> dbList= null;
	private DB_INFO dbInfo = null;
	
	public DbStoreEvent(List<JSONObject> dbList, DB_INFO dbInfo) {
		super(dbList);
		this.dbList = dbList;
		this.dbInfo = dbInfo;
	}
	
	public List<JSONObject> getDbList() {
		return dbList;
	}
	
	public DB_INFO getDbInfo() {
		return dbInfo;
	}

	@Override
	public Object getSource() {
		return super.getSource();
	}


	public enum DB_INFO {
		TEST1("test1"),
		TEST2("test2");
				
		private String name;
		private DB_INFO(String name){
			this.name = name;
		}
		public String getName() {
			return name;
		}		
	}
}
