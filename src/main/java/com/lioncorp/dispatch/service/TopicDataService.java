package com.lioncorp.dispatch.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lioncorp.common.thrift.PoolConfig;
import com.lioncorp.common.thrift.ServiceInfo;
import com.lioncorp.common.thrift.ShardedThriftClientPool;
import com.lioncorp.common.thrift.ThriftClientPool;
import com.lioncorp.common.thrift.iface.ExtractRequestInfo;
import com.lioncorp.common.thrift.iface.ExtractResultInfo;
import com.lioncorp.common.thrift.iface.ExtractService;
import com.lioncorp.common.thrift.iface.ExtractService.Client;
import com.lioncorp.common.thrift.iface.ExtractService.Iface;
import com.lioncorp.common.util.Constants;
import com.lioncorp.common.util.MurmurHash;

/**
 * @author bjssgong
 *
 */
@Service
public class TopicDataService extends BaseService implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(TopicDataService.class);
	
	private ShardedThriftClientPool<String, ExtractService.Client> parserDataPool = null;

	@Override
	public void afterPropertiesSet() throws Exception {	
		String address = Constants.THRIFT_SERVER.TOPIC_ADDRESS;
		if(StringUtils.isBlank(address))
			//
			return;
		List<List<ServiceInfo>> servicePartitions = getServerAddress(address);
		PoolConfig config = new PoolConfig();
		config.setFailover(true);		
		parserDataPool = new ShardedThriftClientPool<>(
			 	servicePartitions, //
                key -> Math.abs(MurmurHash.murmurHash32(key)), //
                servers -> new ThriftClientPool<>(
                		servers, transport -> new Client(new TBinaryProtocol(
        						new TFramedTransport(transport))), config));
		logger.info("init topic shard pool");
	}
	
	public List<ExtractResultInfo> parserNews(List<JSONObject> topicRequest) {

		if(null == parserDataPool)
			return Collections.emptyList();
				
		if(null == topicRequest 
				|| topicRequest.isEmpty())
			return Collections.emptyList();
		
		List<ExtractResultInfo> response = null;
		List<ExtractRequestInfo> requestList = Lists.newArrayList();
		try {
			Iface iface = parserDataPool.getShardedPoolByHash("test").iface();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
