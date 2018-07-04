package com.lioncorp.dispatch.listener;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.dispatch.event.KafkaStoreEvent;
import com.lioncorp.dispatch.listener.BaseListener;
import com.lioncorp.dispatch.service.KafkaStoreService;

@Component
public class KafkaStoreListener extends BaseListener implements SmartApplicationListener {
	private static final Logger logger = LoggerFactory.getLogger(KafkaStoreListener.class);

	@Resource
	private KafkaStoreService kafkaProducerServer;
	
	@Override
	public int getOrder() {
		return KAFKA;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent arg0){
		KafkaStoreEvent kafka = (KafkaStoreEvent) arg0;
		List<JSONObject> allList = (List<JSONObject>)kafka.getSource();
		if (null == allList 
				|| allList.isEmpty()
				|| null == kafka.getKafkaTopic()) {
			return;
		}

	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> arg0) {
		return arg0 == KafkaStoreEvent.class;
	}


	@Override
	public boolean supportsSourceType(Class<?> arg0) {
		return true;
	}

}
