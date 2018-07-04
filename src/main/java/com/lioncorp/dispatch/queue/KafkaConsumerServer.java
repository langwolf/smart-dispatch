package com.lioncorp.dispatch.queue;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.util.ConcurrencyThrottleSupport;

import com.alibaba.fastjson.JSONObject;
import com.lioncorp.dispatch.service.AssembleDataService;
import com.lioncorp.dispatch.service.AssembleOrigiDataService;
import com.lioncorp.dispatch.service.BaseAssembleByQueue;
import com.lioncorp.dispatch.service.BaseService;
import com.lioncorp.dispatch.service.PublisherDataService;

public class KafkaConsumerServer extends ConcurrencyThrottleSupport implements
		MessageListener<String, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory
			.getLogger(KafkaConsumerServer.class);

	public KafkaConsumerServer() {
		setConcurrencyLimit(BaseService.KAFKA_THREAD_LIMIT);
	}

	@Resource
	private PublisherDataService publisherDataService;
	@Resource
	private AssembleOrigiDataService assembleOrigiDataService;
	@Resource
	private AssembleDataService assembleApolloService;

	@Resource(name = "testExecutor")
	private TaskExecutor taskExecutor;

	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		taskExecutor.execute(() -> {
			beforeAccess();
			boolean isChannel = false;
			try {
				logger.info("kafka consumer start");
				String topic = data.topic();
				String key = data.key();
				int partition = data.partition();
				long offset = data.offset();
				String msg = data.value();
				List<JSONObject> dataList = null;
				JSONObject obj = JSONObject.parseObject(msg);
				if (topic.equals("test1")) { // 头条
					final List<JSONObject> list = Collections
							.singletonList(obj);
					dataList = assembleOrigiDataService
							.asyncAssembleData(new BaseAssembleByQueue() {
								@Override
								public List<JSONObject> getData() {
									return assembleApolloService
											.assembleData(list);
								}

							});

				} else if (topic.equals("test2")) {

				} else if (topic.equals("test3")) {

				}
				if (null == dataList || dataList.isEmpty()) {
					logger.info("publisherdata, data is null");
					return;
				}
				publisherDataService.publisherData(dataList, isChannel);
			} catch (Exception e) {
				logger.error("kafka consumer error:{}", e.getMessage());
				e.printStackTrace();
			} finally {
				afterAccess();
			}
		});
	}
}
