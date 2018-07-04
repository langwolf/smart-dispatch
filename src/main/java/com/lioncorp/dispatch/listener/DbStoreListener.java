package com.lioncorp.dispatch.listener;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.dispatch.event.DbStoreEvent;
import com.lioncorp.dispatch.service.DbStoreService;

@Component
public class DbStoreListener extends BaseListener implements SmartApplicationListener {
	private static final Logger logger = LoggerFactory.getLogger(DbStoreListener.class);

	@Resource
	private DbStoreService dbDataService;
	
	@Override
	public int getOrder() {
		return DB;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0){
		DbStoreEvent db = (DbStoreEvent) arg0;
		List<JSONObject> allList = (List<JSONObject>)db.getSource();
		if (null == allList || allList.isEmpty()) {
			return;
		}

	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> arg0) {
		return arg0 == DbStoreEvent.class;
	}


	@Override
	public boolean supportsSourceType(Class<?> arg0) {
		return true;
	}

}
