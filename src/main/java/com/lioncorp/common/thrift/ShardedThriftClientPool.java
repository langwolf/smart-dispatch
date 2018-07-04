package com.lioncorp.common.thrift;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lioncorp.common.exception.NoBackendServiceException;

/**
 * shad
 * @author bjssgong
 *
 * @param <K>
 * @param <T>
 */
public class ShardedThriftClientPool<K, T extends TServiceClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Function<K, Integer> hashFunction;

    private final Function<List<ServiceInfo>, ThriftClientPool<T>> clientPoolFunction;

    private ConcurrentHashMap<Integer, ThriftClientPool<T>> poolMap;

    private List<List<ServiceInfo>> servicePartitions;

    public ShardedThriftClientPool(List<List<ServiceInfo>> serviceList,
            Function<K, Integer> hashFunction,
            Function<List<ServiceInfo>, ThriftClientPool<T>> clientPoolFunction) {

        this.hashFunction = hashFunction;
        this.clientPoolFunction = clientPoolFunction;

        init(serviceList);
    }

    private void init(List<List<ServiceInfo>> services) {
        if (services == null || services.size() == 0) {
            throw new IllegalArgumentException("serviceList is empty");
        }
        poolMap = new ConcurrentHashMap<>();
        this.servicePartitions = services;
        if (servicePartitions == null || servicePartitions.size() == 0) {
            throw new IllegalStateException("partitionFunction should not return empty");
        }
    }

    public ThriftClientPool<T> getShardedPool(K key) throws NoBackendServiceException {
        int hash = hashFunction.apply(key);
        int shard = hash;// 
        List<ServiceInfo> servers = servicePartitions.get(shard);
        if (servers == null || servers.size() == 0) {
            throw new NoBackendServiceException("no servers mapping for key: " + key);
        }

        ThriftClientPool<T> pool = poolMap.get(shard);
        if (pool == null) {
                pool = poolMap.get(shard);
                if (pool == null) {
                    logger.debug("init client pool: shard={}, servers={}", shard, servers);
                    pool = clientPoolFunction.apply(servers);
                    poolMap.put(shard, pool);
                }
        }
        return pool;
    }

    public ThriftClientPool<T> getShardedPoolByHash(K key) throws NoBackendServiceException {
        int hash = hashFunction.apply(key);
        int shard = hash % servicePartitions.size();
        logger.info("getPool by key: hash={}, shard={}/{}", hash, shard, servicePartitions.size());
        
        List<ServiceInfo> servers = servicePartitions.get(shard);
        if (servers == null || servers.size() == 0) {
            throw new NoBackendServiceException("no servers mapping for key: " + key);
        }

        ThriftClientPool<T> pool = poolMap.get(shard);
        if (pool == null) {
                pool = poolMap.get(shard);
                if (pool == null) {
                    logger.debug("init client pool: shard={}, servers={}", shard, servers);
                    pool = clientPoolFunction.apply(servers);
                    poolMap.put(shard, pool);
                }
        }
        return pool;
    }
    
    public int getPartitionSize() {
        return servicePartitions.size();
    }
}
