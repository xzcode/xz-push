package com.xzcode.push.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xzcode.push.IPushService;
import com.xzcode.push.PushService;
import com.xzcode.push.core.config.PushConfig;


@Configuration
@ConditionalOnProperty(prefix = "xz.push", name="enabled", havingValue = "true")
@ConditionalOnMissingBean({IPushService.class})
public class PushAutoConfig {
	
	@Bean
	public IPushService pushService() {
		IPushService pushService = new PushService();
		pushService.setPushconfig(pushConfig());
		pushService.init();
		return pushService;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "xz.push")
	public PushConfig pushConfig() {
		return new PushConfig();
	}

}
