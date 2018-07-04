package com.lioncorp.common.util;

import java.io.Serializable;

import com.lioncorp.common.exception.BusinessException;

public class WebResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer code = null;
	private String message = "";
	private Object data = null;
	private String requestId = "";

	public WebResult() {
		this.code = ResultCode.SUCCESS.getCode();
		this.message = ResultCode.SUCCESS.getDesc();
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
//		NullUtil.removeNull(data);
		this.data = data;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setResultCode(ResultCode code) {
		this.code = code.getCode();
		this.message = code.getDesc();
	}

	public void setException(Exception e, ResultCode result) {
		if (e != null) {
			if (e instanceof BusinessException) {
				BusinessException be = (BusinessException) e;
				this.code = be.getCode();
				this.message = be.getMessage();
			} else {
				setResultCode(result);
			}
		}
		this.data = null;
	}
}
