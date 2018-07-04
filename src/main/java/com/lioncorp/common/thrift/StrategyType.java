package com.lioncorp.common.thrift;


public enum StrategyType {
	RANDOM(1),
	WEIGHTRANDOM(2),
	IPHASH(3),
	ROUNDROBIN(4);
	
	private int code;
	
	private StrategyType(int code){
		this.code = code;
	}
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
