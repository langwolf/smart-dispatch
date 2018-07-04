package com.lioncorp.dispatch.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.dispatch.event.RedisStoreEvent;

@Component
public class RedisStoreListener extends BaseListener implements SmartApplicationListener {
	private static final Logger logger = LoggerFactory.getLogger(RedisStoreListener.class);

	
	@Value("${headline.env}")
	private String environment;
	
	@Override
	public int getOrder() {
		return REDIS;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0){
		RedisStoreEvent ctr = (RedisStoreEvent) arg0;
		List<JSONObject> allList = (List<JSONObject>)ctr.getSource();
		if (null == allList || allList.isEmpty()) {
			return;
		}

	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> arg0) {
		return arg0 == RedisStoreEvent.class;
	}


	@Override
	public boolean supportsSourceType(Class<?> arg0) {
		return true;
	}

}
