package com.lioncorp.common.thrift;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.lioncorp.common.exception.ConnectionFailException;
import com.lioncorp.common.exception.NoBackendServiceException;
import com.lioncorp.common.exception.ThriftException;

/**
 * pool
 * @author bjssgong
 *
 * @param <T>
 */
public class ThriftClientPool<T extends TServiceClient> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Function<TTransport, T> clientFactory;

    private final GenericObjectPool<ThriftClient<T>> pool;

    private List<ServiceInfo> services;

    private boolean serviceReset = false;

    private final PoolConfig poolConfig;

    public ThriftClientPool(List<ServiceInfo> services, Function<TTransport, T> factory) {
        this(services, factory, new PoolConfig(), null);
    }


    public ThriftClientPool(List<ServiceInfo> services, Function<TTransport, T> factory,
            PoolConfig config) {
        this(services, factory, config, null);
    }

    public ThriftClientPool(List<ServiceInfo> services, Function<TTransport, T> factory,
            PoolConfig config, ThriftProtocolFactory pFactory) {
        if (services == null || services.size() == 0) {
            throw new IllegalArgumentException("services is empty!");
        }
        if (factory == null) {
            throw new IllegalArgumentException("factory is empty!");
        }
        if (config == null) {
            throw new IllegalArgumentException("config is empty!");
        }

        this.services = services;
        this.clientFactory = factory;
        this.poolConfig = config;
        // test if config change; true->false
        this.poolConfig.setMaxTotal(PoolConfig.MAX_TOTAL);
        this.poolConfig.setTestOnReturn(false); 
        this.poolConfig.setTestOnBorrow(false);
        this.pool = new GenericObjectPool<>(new BasePooledObjectFactory<ThriftClient<T>>() {

            @Override
            public ThriftClient<T> create() throws Exception {

                // global list
                List<ServiceInfo> serviceList = ThriftClientPool.this.services;
                ServiceInfo serviceInfo = getStrategyService(poolConfig.getStrategy(),
                		serviceList);
                TTransport transport = getTransport(serviceInfo);

                try {
                    transport.open();
                } catch (TTransportException e) {
                    logger.info("transport open fail service: host={}, port={}",
                            serviceInfo.getHost(), serviceInfo.getPort());
                    if (poolConfig.isFailover()) {
                         while (true) {
                            try {
                                serviceList = removeFailService(serviceList, serviceInfo);
                                serviceInfo = getStrategyService(poolConfig.getStrategy(),
                                		serviceList);
                                transport = getTransport(serviceInfo); // while break here
                                logger.info("failover to next service host={}, port={}",
                                        serviceInfo.getHost(), serviceInfo.getPort());
                                transport.open();
                                break;
                            } catch (TTransportException e2) {
                                logger.warn("failover fail, services left: {}", serviceList.size());
                            }
                        }
                    } else {
                        throw new ConnectionFailException("host=" + serviceInfo.getHost() + ", ip="
                                + serviceInfo.getPort(), e);
                    }
                }

                ThriftClient<T> client = new ThriftClient<>(clientFactory.apply(transport), pool,
                        serviceInfo);

                logger.debug("create new object for pool {}", client);
                return client;
            }

			@Override
            public PooledObject<ThriftClient<T>> wrap(ThriftClient<T> obj) {
                return new DefaultPooledObject<>(obj);
            }

            @Override
            public boolean validateObject(PooledObject<ThriftClient<T>> p) {
                ThriftClient<T> client = p.getObject();

                // check if return client in current service list if 
                if (serviceReset) {
                    if (!ThriftClientPool.this.services.contains(client.getServiceInfo())) {
                        logger.warn("not return object because it's from previous config {}",
                                client);
                        client.closeClient();
                        return false;
                    }
                }

                return super.validateObject(p);
            }

            @Override
			public void activateObject(PooledObject<ThriftClient<T>> p)
					throws Exception {
            	if (!p.getObject().iFace().getInputProtocol().getTransport().isOpen()) {
                    //p.getObject().iFace().getInputProtocol().getTransport().open();
                }
			}

			@Override
			public void passivateObject(PooledObject<ThriftClient<T>> p)
					throws Exception {
				super.passivateObject(p);
			}

			@Override
            public void destroyObject(PooledObject<ThriftClient<T>> p) throws Exception {
                p.getObject().closeClient();
                super.destroyObject(p);
            }
			
        }, poolConfig);
    }

    public List<ServiceInfo> getServices() {
        return services;
    }

    /**
     * set new services for this pool
     *
     * @param services
     */
    public void setServices(List<ServiceInfo> services) {
        if (services == null || services.size() == 0) {
            throw new IllegalArgumentException("services is empty!");
        }
        this.services = services;
        serviceReset = true;
    }

    private TTransport getTransport(ServiceInfo serviceInfo) {

        if (serviceInfo == null) {
            throw new NoBackendServiceException();
        }

        TTransport transport;
        if (poolConfig.getTimeout() > 0) {
            transport = new TSocket(serviceInfo.getHost(), serviceInfo.getPort(),
                    poolConfig.getTimeout());
        } else {
            transport = new TSocket(serviceInfo.getHost(), serviceInfo.getPort());
        }
        return transport;
    }

    private ServiceInfo getStrategyService(StrategyType type, List<ServiceInfo> serviceList) {
        if (serviceList == null || serviceList.size() == 0) {
            return null;
        }
        ServiceInfo serviceInfo = null; 
        if (type.getCode() == StrategyType.RANDOM.getCode()) {
        	serviceInfo = StrategyService.random(serviceList);
        } else if (type.getCode() == StrategyType.ROUNDROBIN.getCode()) {
        	serviceInfo = StrategyService.roundRobin(serviceList);
        }     
        return serviceInfo;
    }

    private List<ServiceInfo> removeFailService(List<ServiceInfo> list, ServiceInfo serviceInfo) {
        logger.info("remove service from current service list: host={}, port={}",
                serviceInfo.getHost(), serviceInfo.getPort());
        return list.stream() //
                .filter(si -> !serviceInfo.equals(si)) //
                .collect(Collectors.toList());
    }

    public ThriftClient<T> getClient() throws ThriftException {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            if (e instanceof ThriftException) {
                throw (ThriftException) e;
            }
            throw new ThriftException("Get client from pool failed.", e);
        }
    }


    @SuppressWarnings("unchecked")
    public <X> X iface() throws ThriftException {
        ThriftClient<T> client;
        try {
            client = pool.borrowObject();
        } catch (Exception e) {
            if (e instanceof ThriftException) {
                throw (ThriftException) e;
            }
            throw new ThriftException("Get client from pool failed.", e);
        }
        AtomicBoolean returnToPool = new AtomicBoolean(false);
        return (X) Proxy.newProxyInstance(this.getClass().getClassLoader(), client.iFace()
                .getClass().getInterfaces(), (proxy, method, args) -> {
            if (returnToPool.get()) {
                throw new IllegalStateException("Object returned via iface can only used once!");
            }
            boolean success = false;
            try {
                Object result = method.invoke(client.iFace(), args);
                success = true;
                return result;
            } catch (Throwable e) {
                logger.warn("invoke fail", e);
                throw e;
            } finally {
                if (success) {
                    pool.returnObject(client);
                } else {
                    client.closeClient();
                    pool.invalidateObject(client);
                }
                returnToPool.set(true);
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        if (pool != null) {
            pool.close();
        }
        super.finalize();
    }
    
    
    /**
     * server, allot strategy
     * @author bjssgong
     *
     */
    public static class StrategyService {
    	
    	private static Map<String,Integer> serverWeigthMap  = Maps.newHashMap();
        private static AtomicInteger pos = new AtomicInteger();
    	static{
    		serverWeigthMap.put("10.200.131.26:28001", 1);
    		serverWeigthMap.put("10.200.131.26:28002", 1);
    		serverWeigthMap.put("10.200.131.26:28003", 2);
    		serverWeigthMap.put("10.200.131.26:28004", 2);
    		serverWeigthMap.put("10.200.131.26:28005", 3);
    		serverWeigthMap.put("10.200.131.26:28006", 3);
    	}

    	 /**
    	  * 获取请求服务器地址
    	  * @param remoteIp 负载均衡服务器ip
    	  * @return
    	  */
    	public static String ipHash(String remoteIp) {
    		Map<String,Integer> serverMap  = Maps.newHashMap();
    		serverMap.putAll(serverWeigthMap);
    		Set<String> keySet = serverMap.keySet();
    		ArrayList<String> keyList = new ArrayList<String>();
    		keyList.addAll(keySet);
    		
    		int hashCode = remoteIp.hashCode();
    		int serverListSize = keyList.size();
    		int serverPos = hashCode % serverListSize;
    		
    		return keyList.get(serverPos);
    	}   	
    	
   	 	public static ServiceInfo roundRobin(List<ServiceInfo> serviceList) {   
   	 		if (serviceList == null || serviceList.size() == 0) {
	 			return null;
	 		}
   	 		ServiceInfo server = null;
   			int value = pos.incrementAndGet();
   			if(value >= serviceList.size()){
   				value = pos.getAndSet(0);
   			} 
   			server = serviceList.get((value > 0)? (value-1) : 0);
   			return server;
   		}
   	 	
   	 	public static ServiceInfo random(List<ServiceInfo> serviceList) {
   	 		if (serviceList == null || serviceList.size() == 0) {
   	 			return null;
   	 		}
   	 		return serviceList.get(RandomUtils.nextInt(0, serviceList.size()));
   	 	}
    }
//    public static ThreadPoolExecutor executorService = new ThreadPoolExecutor(
//            Runtime.getRuntime().availableProcessors(), Runtime.getRuntime()
//                    .availableProcessors() + 3, 1, TimeUnit.MINUTES,
//            new ArrayBlockingQueue<Runnable>(100), new NamedThreadFactory(
//                    "SearchImport"),
//            new ThreadPoolExecutor.CallerRunsPolicy());
//    public static void main(String[] args) throws Exception {
//    	for(int i =0; i < 100; i++){
//    		executorService.submit(() -> {
//    			Strategy.roundRobin();
//    		});
//    	}
//    }
    
}
