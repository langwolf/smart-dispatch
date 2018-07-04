package com.lioncorp.dispatch.service;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.dispatch.event.DbStoreEvent.DB_INFO;
import com.lioncorp.dispatch.event.KafkaStoreEvent.KAFKA_TOPIC;
import com.lioncorp.dispatch.event.RedisStoreEvent.LOCAL_PATH;

@Service
public class PublisherDataService extends BaseService {
	private static Logger logger = LoggerFactory.getLogger(PublisherDataService.class);

	@Resource
	private DataPublisher dataPublisher;


	public void publisherData(List<JSONObject> dataList, boolean isChannel) {
		if(null == dataList || dataList.isEmpty()){
			logger.info("publisher data, data is null");
			return;
		}
		dataPublisher.redisStore(dataList, LOCAL_PATH.TEST1);
		dataPublisher.dbStore(dataList, DB_INFO.TEST1);
		dataPublisher.kafkaStore(dataList, KAFKA_TOPIC.TEST1);

	}
}
