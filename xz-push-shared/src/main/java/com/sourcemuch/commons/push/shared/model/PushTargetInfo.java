package com.sourcemuch.commons.push.shared.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 消息推送对象
 * 
 * @author zai
 * 2017-03-27 09:50:18
 */
public class PushTargetInfo {
	
	private List<String> payloads;//设置要发送的消息内容payload, 不允许全是空白字符, 长度小于4K, 一个中英文字符均计算为1(透传消息回传给APP, 为必填字段, 非透传消息可选)
	private List<String> titles = new LinkedList<>();//设置在通知栏展示的通知的标题, 不允许全是空白字符, 长度小于16, 一个中英文字符均计算为1(通知栏消息必填)
	private List<String> descriptions = new LinkedList<>();//设置在通知栏展示的通知描述, 不允许全是空白字符, 长度小于128, 一个中英文字符均计算为1(通知栏消息必填)
	private List<String> messageTypes = new LinkedList<>();//消息类型
	private List<Map<String, String>> datas = new ArrayList<>();//消息类型
	private String target;
	private String deviceType;
	
	
	private PushTargetInfo addPayload(String payload) {
		this.payloads.add(payload);
		return this;
	}
	
	private PushTargetInfo addTitles(String title) {
		this.titles.add(title);
		return this;
	}
	
	private PushTargetInfo addDescriptions(String description) {
		this.descriptions.add(description);
		return this;
	}
	
	private PushTargetInfo addMessageType(String messageType) {
		this.messageTypes.add(messageType);
		return this;
	}
	
	private PushTargetInfo addDatas(Map<String, String> data) {
		this.datas.add(data);
		return this;
	}
	
	public PushTargetInfo addInfo(String messageType,String title, String payload, String description) {
		return this
		.addTitles(title)
		.addPayload(payload)
		.addDescriptions(description)
		.addMessageType(messageType);
	}
	
	public PushTargetInfo addInfo(String messageType,String title, String payload, String description, Map<String, String> data) {
		return this
		.addTitles(title)
		.addPayload(payload)
		.addDescriptions(description)
		.addMessageType(messageType)
		.addDatas(data)
		;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public List<String> getPayloads() {
		return payloads;
	}

	public void setPayloads(List<String> payloads) {
		this.payloads = payloads;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	public List<String> getMessageTypes() {
		return messageTypes;
	}

	public void setMessageTypes(List<String> messageTypes) {
		this.messageTypes = messageTypes;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public List<Map<String, String>> getDatas() {
		return datas;
	}
	
}
