package com.lioncorp.dispatch.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.lioncorp.common.util.PropertyUtil;
import com.lioncorp.dispatch.event.KafkaStoreEvent.KAFKA_TOPIC;

@Service
public class KafkaStoreService extends BaseService {
	private static Logger logger = LoggerFactory
			.getLogger(KafkaStoreService.class);
	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String IFPARTITION = "0";

	public void transferWithJson(Object value, KAFKA_TOPIC kafkaTopicName) {
		if (null == kafkaTopicName)
			return;
		if (StringUtils.isNotBlank(kafkaTopicName.getName())) {
			String kafkaTopic = PropertyUtil.get(kafkaTopicName.getName());
			if (StringUtils.isNotBlank(kafkaTopic)) {
				sndMesForTemplate(kafkaTopic, value, "1", 0, "online");
			}
		}
	}

	public Map<String, Object> sndMesForTemplate(String topic, Object value,
			String ifPartition, Integer partitionNum, String role) {
		String key = role + "-" + value.hashCode();
		String valueString = gson.toJson(value);
		if (ifPartition.equals(IFPARTITION)) {
			int partitionIndex = getPartitionIndex(key, partitionNum);
			ListenableFuture<SendResult<String, String>> result = kafkaTemplate
					.send(topic, partitionIndex, key, valueString);
			Map<String, Object> res = checkProRecord(result);
			return res;
		} else {
			ListenableFuture<SendResult<String, String>> result = kafkaTemplate
					.send(topic, valueString);
			Map<String, Object> res = checkProRecord(result);
			return res;
		}
	}

	private int getPartitionIndex(String key, int partitionNum) {
		if (key == null) {
			Random random = new Random();
			return random.nextInt(partitionNum);
		} else {
			int result = Math.abs(key.hashCode()) % partitionNum;
			return result;
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> checkProRecord(
			ListenableFuture<SendResult<String, String>> res) {
		Map<String, Object> m = new HashMap<String, Object>();
		if (res != null) {
			try {
				SendResult r = res.get();
				Long offsetIndex = r.getRecordMetadata().offset();
				if (offsetIndex != null && offsetIndex >= 0) {
					m.put("code", 1);
					m.put("message", "");
					return m;
				} else {
					m.put("code", 1);
					m.put("message", "");
					return m;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				m.put("code", 0);
				m.put("message", "");
				return m;
			} catch (ExecutionException e) {
				e.printStackTrace();
				m.put("code", 0);
				m.put("message", "");
				return m;
			}
		} else {
			m.put("code", 2);
			m.put("message", "");
			return m;
		}
	}

}
