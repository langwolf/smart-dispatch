package com.lioncorp.dispatch.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.alibaba.fastjson.JSONObject;

public class KafkaStoreEvent extends ApplicationEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JSONObject> result = null;
	private KAFKA_TOPIC kafkaTopic = null;
	
	public KafkaStoreEvent(List<JSONObject> result, KAFKA_TOPIC kafkaTopic) {
		super(result);		
		this.result = result;
		this.kafkaTopic= kafkaTopic;
	}
	
	public List<JSONObject> getResult() {
		return result;
	}

	public KAFKA_TOPIC getKafkaTopic() {
		return kafkaTopic;
	}

	@Override
	public Object getSource() {
		return super.getSource();
	}

	public enum KAFKA_TOPIC {
		TEST1("kafka.product.topic.name1"),
		TEST2("kafka.product.topic.name2"),
		TEST3("kafka.product.topic.name3");
		
		private String name;
		private KAFKA_TOPIC(String name){
			this.name = name;
		}
		public String getName() {
			return name;
		}
		
	}
}
