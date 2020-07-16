package com.spring.security.constant;

import java.util.Objects;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-29
 * @Time: 9:11
 * @Version: eoms-micro-service 1.0
 */
public enum BusinessConstant {

    /********************  业务  *********************/

	/**
	 * 成功
	 */
	KEY_SUCCESS(200, "OK"),

	/**
	 * 失败
	 */
	KEY_FAILED(500, "Internal Server Error"),

	/**
	 * 错误请求
	 */
	KEY_UNKNOWN(400, "Bad Request"),

	/**
	 * 未经授权
	 */
	KEY_UNAUTHORIZED(401, "Unauthorized"),

	/**
	 * 未找到资源
	 */
	KEY_NOTFOUND(404, "Not Found"),

	/**
	 * 请求方法不能被用于请求相应的资源
	 */
	KEY_CANNOTRESOURCE(405, "Cannot Be Resource"),


    /***********************  校验  **********************/

    /**
     * 错误
     */
    ERROR(0, "ERROR"),

    /**
     * 空
     */
    EMPTY(1, "EMPTY"),

    /**
     * 失败
     */
    FAILED(2, "FAILED"),

    /**
     * NULL
     */
    NULL(3, "NULL"),

    /**
     * 找不到
     */
    NOT_FOUND(4, "NOT FOUND"),

    /**
     * 不可用
     */
    UNAVAILABLE(5, "UNAVAILABLE"),

    /**
     * 超时
     */
    EXPIRED(6, "EXPIRED"),

    /**
     * 非法
     */
    INVALID(7, "INVALID"),

    /**
     * 拒绝
     */
    DENIED(8, "DENIED");


	private int code;
	private String msg;

	BusinessConstant() {}

	BusinessConstant(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @desc 根据code获取message
	 * @param code
	 * @return
	 */
	public static String getMessage(int code){
		for(BusinessConstant baseConstant : values()){
			if(Objects.equals(code, baseConstant.code)){
				return baseConstant.msg;
			}
		}
		return null;
	}

}
