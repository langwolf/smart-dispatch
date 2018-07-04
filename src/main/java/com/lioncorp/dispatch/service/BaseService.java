package com.lioncorp.dispatch.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.lioncorp.common.thrift.ServiceInfo;
import com.lioncorp.common.util.Constants;

public class BaseService {
	private static Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	public static final int KAFKA_THREAD_LIMIT = 12;
	public final static String ADD = "add";
	public final static String DEL = "delete";
	
	protected final static String ONLINE = "online";
	protected final static String TEST = "test";
	
	public final static int PAGE_SIZE = 10;
	
	private static Splitter splitter = Splitter.on(';');
	
	private static Splitter splitter2 = Splitter.on(',');
	protected final static Gson gson = new Gson();
	
	
	protected static int TOPIC_TIME_OUT = 8000;
		
	protected static String TOPIC_ADDRESS = Constants.THRIFT_SERVER.TOPIC_ADDRESS;


	public static List<List<ServiceInfo>> getServerAddress(String topicAddress){
		List<List<ServiceInfo>> serverList = Lists.newArrayList();
		List<String> shardList = splitter.splitToList(topicAddress);
		for(String shard : shardList){
			List<String> ipList = splitter2.splitToList(shard);
			List<ServiceInfo> server = Lists.newArrayList();
			for(String ad: ipList){
				server.add(ServiceInfo.of(ad));
			}
			serverList.add(server);
		}
		return serverList;
	}
}
