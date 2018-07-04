package com.lioncorp.common.thrift;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


public class PoolConfig extends GenericObjectPoolConfig {

    private int timeout = 0;

    protected static int MAX_TOTAL = 10;
    
    private boolean failover = false;
    
    private StrategyType strategy = StrategyType.RANDOM;
    
    public StrategyType getStrategy() {
		return strategy;
	}

	public void setStrategy(StrategyType strategy) {
		this.strategy = strategy;
	}

	/**
     * socket 超时时间
     * 默认 0 不超时
     * @return
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * socket 超时时间
     * 
     * @param  millis
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    
    /**
     * 若服务失败，连接到下一个服务
     * 默认false
     * @return
     */
    public boolean isFailover() {
        return failover;
    }

    /**
     * 设置连接到下一个服务
     * 
     * @param
     */
    public void setFailover(boolean failover) {
        this.failover = failover;
    }
}
