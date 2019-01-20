package com.xzcode.push;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Message.Builder;
import com.xiaomi.xmpush.server.Message.IOSBuilder;
import com.xzcode.push.core.config.PushConfig;
import com.xzcode.push.core.constant.PushConstant;
import com.xzcode.push.core.exception.PushException;
import com.xzcode.push.core.model.PushInfo;
import com.xzcode.push.core.model.PushTargetInfo;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import com.xiaomi.xmpush.server.TargetedMessage;

public class PushService implements IPushService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushService.class);
	
	private static final Gson GSON = new Gson();
	
	private PushConfig pushconfig;
	
	@Override
	public void init() {
		if ("test".equals(pushconfig.getEnv().trim())) {
			Constants.useSandbox();
		}else {
			Constants.useOfficial();
		}
	}
	
	
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
	@Override
	public void sendCommonMessage(PushInfo pushInfo) throws Exception {
	    //Constants.useOfficial();
		
		if(pushInfo.getAndroidTargets().isEmpty() && pushInfo.getIosTargets().isEmpty()){
			PushException.throwz("推送目标不能为空!");
		}
		
		if(pushInfo.getAndroidTargets().size() + pushInfo.getAndroidTargets().size() > 1000){
			PushException.throwz("每次推送目标数量不能大于1000!");
		}
		
		
		Result result = null;
		
		if (!pushInfo.getAndroidTargets().isEmpty()) {
			Message message = buildAndroidMessage(
    				pushInfo.getTitle(),
    				pushInfo.getPayload(),
    				pushInfo.getDescription(),
    				pushInfo.getMessageType(),
    				pushInfo.getData()
	    			);
			Sender sender = new Sender(pushconfig.getAndroid_appsecret());
			//发送消息到一组设备上, regids个数不得超过1000个
		    result = sender.sendToAlias(message, pushInfo.getAndroidTargets(), pushconfig.getRetries());
		    this.showResult(result);
		}
		
		if (!pushInfo.getIosTargets().isEmpty()) {
			Message message = buildIosMessage(
    				pushInfo.getTitle(),
    				pushInfo.getPayload(),
    				pushInfo.getDescription(),
    				pushInfo.getMessageType(),
    				pushInfo.getData()
	    			);
			Sender sender = new Sender(pushconfig.getIos_appsecret());
			//发送消息到一组设备上, regids个数不得超过1000个
		    result = sender.sendToAlias(message, pushInfo.getIosTargets(), pushconfig.getRetries());
		    this.showResult(result);
		    
		}
		
		
		
	    
	}
	
	private void showResult(Result result){
		if (LOGGER.isDebugEnabled()) {
	    	if (result != null) {
	    		LOGGER.debug("小米推送结果:{}", GSON.toJson(result));
			}
		}
	}
	
	/**
	 * 发送多条消息给一个用户
	 * @param pushInfo
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-06-21
	 */
	@Override
	public void sendTargetedMessage(PushTargetInfo pushInfo) throws Exception {
		
		if(pushInfo == null) {
			PushException.throwz("推送集合不能为空!");
		}
		
		if(pushInfo.getTitles().size() > 1000) {
			PushException.throwz("推送目标数量不能大于1000!");
		}
		
		if (pushInfo.getDeviceType() == null) {
	    	PushException.throwz("推送目标的DeviceType不能为空!");
		}
	    
	    
	    List<TargetedMessage> messages = new ArrayList<>(pushInfo.getTitles().size());
	    
	   
	    Message message = null;
	    for (int i = 0; i < messages.size(); i++) {
	    	
	    	if (PushConstant.DeviceTypeConstant.MOBILE_ANDROID.equals(pushInfo.getDeviceType())) {
	    		
	    		message = buildAndroidMessage(
	    				pushInfo.getTitles().get(i),
	    				pushInfo.getPayloads().get(i),
	    				pushInfo.getDescriptions().get(i),
	    				pushInfo.getMessageTypes().get(i),
						pushInfo.getDatas().get(i)
		    			);
			}else if (PushConstant.DeviceTypeConstant.MOBILE_IOS.equals(pushInfo.getDeviceType())){
				message = buildIosMessage(
						pushInfo.getTitles().get(i),
						pushInfo.getPayloads().get(i),
						pushInfo.getDescriptions().get(i),
						pushInfo.getMessageTypes().get(i),
						pushInfo.getDatas().get(i)
		    			);
			}
	    	
	    	TargetedMessage targetedMessage = new TargetedMessage();
	    	targetedMessage.setMessage(message);
	    	
	    	
	    	targetedMessage.setTarget( TargetedMessage.TARGET_TYPE_ALIAS, pushInfo.getTarget());
	    	messages.add(targetedMessage);
		}
	    Sender sender = null;
	    if (PushConstant.DeviceTypeConstant.MOBILE_ANDROID.equals(pushInfo.getDeviceType())) {
	    	sender = new Sender(pushconfig.getAndroid_appsecret());
	    }else if (PushConstant.DeviceTypeConstant.MOBILE_IOS.equals(pushInfo.getDeviceType())){
	    	sender = new Sender(pushconfig.getIos_appsecret());
	    }
	    
	    sender = new Sender(pushconfig.getIos_appsecret());
	    	
	    Result result = sender.send(messages, pushconfig.getRetries());
	    
	    this.showResult(result);
	    
	}
	
	/**
	 * 创建推送消息
	 * @param pushInfo
	 * @return
	 * @author zai
	 * 2017-03-27 11:13:01
	 */
	private Message buildAndroidMessage(String title, String payload, String description, String messageType, Map<String, String> data) {
			 Builder builder = new Message.Builder()
	                .title(title)
	                .description(description == null ? title : description)
	                .payload(payload == null ? title : payload)
	                .passThrough(0)// 1表示透传消息, 0表示通知栏消息(默认是通知栏消息)
	                .restrictedPackageName(pushconfig.getPackages())
	                .extra(Constants.EXTRA_PARAM_NOTIFY_FOREGROUND, "1")
	                .extra("title", title)
	                .extra("body", payload == null ? title : payload)
	                .extra("messageType", messageType)
	                //yyyy-MM-dd HH:mm:s
	                .extra("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	                
			 		for (String key : data.keySet()) {
			 			builder.extra(key, data.get(key));
			 		}
			 		
			 		builder.timeToLive(pushconfig.getTtl());
		        
	                
	                //.passThrough(1)  //消息使用透传方式
			 		return builder.notifyType(1)     // 使用默认提示音提示
	                // 使用默认提示音提示
	                .build();
	}
	
	
	private Message buildIosMessage(String title, String payload, String description, String messageType, Map<String, String> data) {
		IOSBuilder iosBuilder = new Message.IOSBuilder()
        .title(title)
        .body(payload == null ? title : payload)
        .description(description == null ? title : description)
        .badge(0)                // 数字角标
        .soundURL("default")  
        .category("action")     // 快速回复类别
        .extra("messageType", messageType)
        .extra("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        .extra("title", title)
        .extra("body", payload == null ? title : payload);
        
        for (String key : data.keySet()) {
			iosBuilder.extra(key, data.get(key));
		}
        iosBuilder.timeToLive(pushconfig.getTtl());
		return iosBuilder.build();
        
        
		
		
	}

	

	
	
	public PushConfig getPushconfig() {
		return pushconfig;
	}
	
	@Override
	public void setPushconfig(PushConfig pushconfig) {
		this.pushconfig = pushconfig;
	}
	
	
	
	
	
}
