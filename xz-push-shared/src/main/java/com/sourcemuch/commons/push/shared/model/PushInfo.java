package com.sourcemuch.commons.push.shared.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sourcemuch.commons.push.shared.constant.PushConstant;

/**
 * 消息推送对象
 * 
 * @author zai
 * 2017-03-27 09:50:18
 */
public class PushInfo {
	
	//private String deviceType;//设备类型PushDeviceType
	
	private String payload;//设置要发送的消息内容payload, 不允许全是空白字符, 长度小于4K, 一个中英文字符均计算为1(透传消息回传给APP, 为必填字段, 非透传消息可选)
	private String title;//设置在通知栏展示的通知的标题, 不允许全是空白字符, 长度小于16, 一个中英文字符均计算为1(通知栏消息必填)
	private String description;//设置在通知栏展示的通知描述, 不允许全是空白字符, 长度小于128, 一个中英文字符均计算为1(通知栏消息必填)
	private String messageType;//自定义消息类型
	private List<String> androidTargets = new LinkedList<>();//发送给安卓用户的target集合
	private List<String> iosTargets = new LinkedList<>();//发送给ios用户的target集合
	private Map<String, String> data = new LinkedHashMap<>();//额外字段
	
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public PushInfo addTarget(String target, String deviceType) {
		if (PushConstant.DeviceTypeConstant.MOBILE_ANDROID.equals(deviceType)) {
			this.androidTargets.add(target);
		}else if (PushConstant.DeviceTypeConstant.MOBILE_IOS.equals(deviceType)) {
			this.iosTargets.add(target);
		}
		return this;
	}
	
	public PushInfo addAndroidTarget(String target) {
		this.androidTargets.add(target);
		return this;
	}
	
	public PushInfo addIosTarget(String target) {
		this.iosTargets.add(target);
		return this;
	}
	public List<String> getAndroidTargets() {
		return androidTargets;
	}
	public void setAndroidTargets(List<String> androidTargets) {
		this.androidTargets = androidTargets;
	}
	public List<String> getIosTargets() {
		return iosTargets;
	}
	public void setIosTargets(List<String> iosTargets) {
		this.iosTargets = iosTargets;
	}
	
	
	public void addData(String key, String value) {
		this.data.put(key, value);
	}
	
	public Map<String, String> getData() {
		return data;
	}
	
	
}
