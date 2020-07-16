package com.spring.security.constant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-29
 * @Time: 9:11
 * @Version: eoms-micro-service 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessage<T> implements Serializable {

	public static final long serialVersionUID = 42L;

	private int code;

	private boolean status;

	private String msg;

	private T data;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(String msg, T data) {
		super();
		this.msg = msg;
		this.data = data;
	}

	public ResponseMessage(int code, boolean status, String msg, T data) {
		super();
		this.code = code;
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public static <T> ResponseMessage<T> success() {
		return ResponseMessage.success(null);
	}

	public static <T> ResponseMessage<T> success(T data) {
		return ResponseMessage.success(BusinessConstant.KEY_SUCCESS.getMsg(), data);
	}

	public static <T> ResponseMessage<T> success(String msg, T data) {
		return new ResponseMessage<T>(BusinessConstant.KEY_SUCCESS.getCode(), true, msg, data);
	}

	public static <T> ResponseMessage<T> failed() {
		return ResponseMessage.failed(BusinessConstant.KEY_FAILED.getMsg());
	}

	public static <T> ResponseMessage<T> failed(String msg) {
		return ResponseMessage.failed(msg, null);
	}

	public static <T> ResponseMessage<T> failed(String msg, T data) {
		return new ResponseMessage<T>(BusinessConstant.KEY_FAILED.getCode(), false, msg, data);
	}

	public boolean isSuccessful() {
		return code == BusinessConstant.KEY_SUCCESS.getCode() && status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
