package com.hb.exception;

public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;


	/**
	 * 构造一个异常对象.
	 * @param message
	 * @param cause
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * 构造一个异常对象.
	 * 
	 * @param message
	 *            信息描述
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * 构造一个异常对象.
	 * 
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 */
	public SystemException(String errorCode, String message) {
		super(errorCode, message, true);
	}

	/**
	 * 构造一个异常对象.
	 * 
	 * @param errorCode
	 *            错误编码
	 * @param message
	 *            信息描述
	 * @param propertiesKey
	 *            消息是否为属性文件中的Key
	 */
	public SystemException(String errorCode, String message,
			boolean propertiesKey) {
		super(errorCode, message, propertiesKey);
	}

}
