package com.lioncorp.dispatch.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.lioncorp.common.exception.DeleteException;
import com.lioncorp.dispatch.event.DbStoreEvent.DB_INFO;

public abstract class AbstractDeleteService {
	private Logger logger = LoggerFactory.getLogger(AbstractDataService.class);
	
	protected CacheStoreService storeCacheService;
	protected DbStoreService dbDataService;
	
	public void deleteBjrecData(String docid, String docType, DELETE_ITEM delItem) 
			throws DeleteException {
    	if(StringUtils.isBlank(docid) || null == delItem)
    		return;
    	List<String> tbNameList = Lists.newArrayList();
    	try{      
    		storeCacheService.insertForTest(docid, "");
    		if(delItem.getValue() == DELETE_ITEM.TEST.getValue()) {
    			tbNameList.add(DB_INFO.TEST1.getName());
    		} else if(delItem.getValue() == DELETE_ITEM.ALL.getValue()){

    		} 
        } catch (Exception e){
        	e.printStackTrace();
        }
        try {
        	if(null != tbNameList && !tbNameList.isEmpty()){
        		for(String tbname : tbNameList){
        			//
        		}
        	}
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
	
	protected abstract void deleteBjrecExplorData(String docid, String skipType);
	public enum DELETE_ITEM {
		ALL(0), TEST(1);

		private final int value;

		public int getValue() {
			return value;
		}

		private DELETE_ITEM(int value) {
			this.value = value;
		}

		public static DELETE_ITEM findByValue(int value) {
			switch (value) {
			case 0:
				return ALL;
			case 1:
				return TEST;
			default:
				return null;
			}
		}
	}
}
