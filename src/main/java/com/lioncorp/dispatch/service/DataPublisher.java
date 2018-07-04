package com.lioncorp.dispatch.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.dispatch.event.DbStoreEvent;
import com.lioncorp.dispatch.event.DbStoreEvent.DB_INFO;
import com.lioncorp.dispatch.event.KafkaStoreEvent;
import com.lioncorp.dispatch.event.KafkaStoreEvent.KAFKA_TOPIC;
import com.lioncorp.dispatch.event.RedisStoreEvent;
import com.lioncorp.dispatch.event.RedisStoreEvent.LOCAL_PATH;

@Component
@Configuration
public class DataPublisher implements ApplicationEventPublisherAware {

	private static final Logger logger = LoggerFactory.getLogger(DataPublisher.class);
					
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void redisStore(List<JSONObject> list, LOCAL_PATH ctrPathName){
		RedisStoreEvent tradeEvent = new RedisStoreEvent(list, ctrPathName);
		logger.info("redis info send start");		
		this.applicationEventPublisher.publishEvent(tradeEvent);
		logger.info("redis info send end");
	}
	
	public void dbStore(List<JSONObject> list, DB_INFO dbInfo){
		if (list == null || list.size() == 0 || list.isEmpty()){
			return;
		}
		DbStoreEvent tradeEvent = new DbStoreEvent(list, dbInfo);
		logger.info("db "+dbInfo.getName()+" start");		
		this.applicationEventPublisher.publishEvent(tradeEvent);
		logger.info("db "+dbInfo.getName()+" end");
	}
	
	public void kafkaStore(List<JSONObject> list, KAFKA_TOPIC kafkaTopic){
		KafkaStoreEvent tradeEvent = new KafkaStoreEvent(list, kafkaTopic);
		logger.info("kafka info send start");		
		this.applicationEventPublisher.publishEvent(tradeEvent);
		logger.info("kafka info send end");
	}
	
	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher paramApplicationEventPublisher) {
		this.applicationEventPublisher = paramApplicationEventPublisher;
	}

}
