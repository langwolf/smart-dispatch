package com.lioncorp.common.util;

public enum ResultCode {
	SUCCESS(1, "操作成功"),
	FAIL(-1, "操作失败");
    
    /** 代码值*/
    private int code = 0;
    /** 代码值描述*/
    private String desc = null;

	private ResultCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}