package com.xzcode.push.core.exception;

/**
 * 通用业务异常
 * 
 * @author zai
 * 2017-03-25 03:37:27
 */
public class PushException extends RuntimeException {
	private static final long serialVersionUID = -1715907038757557299L;

	public PushException(String message) {
		super(message);
	}
	
	/**
	 * 修改 fillInStackTrace 不填充线程栈 提高业务异常抛出性能
	 * @return
	 * @author zai
	 * 2017-03-25 03:54:14
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
	
	public static PushException throwz(String message) {
		return new PushException(message);
	}
	
	
	
}
