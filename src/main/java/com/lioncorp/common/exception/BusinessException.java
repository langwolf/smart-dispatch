package com.lioncorp.common.exception;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code = null;

	private String message = null;

	private Object data = null;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public BusinessException(Integer code) {
		this.code = code;
	}

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException(Integer code, String message) {
		this.code = code;
		this.message = message;
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

	@Override
	public String toString() {
		return "exception code:" + code + ", message: " + message;
	}
}