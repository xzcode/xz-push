package com.sourcemuch.commons.push.core;

import com.sourcemuch.commons.push.core.config.PushConfig;
import com.sourcemuch.commons.push.shared.model.PushInfo;
import com.sourcemuch.commons.push.shared.model.PushTargetInfo;

public interface IPushService {

	/**
	 * 发送相同的消息给所有用户
	 * @param appkey
	 * @param packages
	 * @param retries
	 * @param pushInfo
	 * @param regids
	 * @throws Exception
	 * @author zai
	 * 2017-03-27 10:42:50
	 */
	void sendCommonMessage(PushInfo pushInfo) throws Exception;

	/**
	 * 发送多条消息给一个用户
	 * @param pushInfo
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-06-21
	 */
	void sendTargetedMessage(PushTargetInfo pushInfo) throws Exception;

	/**
	 * 设置配置对象
	 * @param pushconfig
	 * 
	 * @author zai
	 * 2017-07-18
	 */
	void setPushconfig(PushConfig pushconfig);

	void init();

}