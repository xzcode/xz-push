package com.sourcemuch.commons.push.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sourcemuch.commons.push.core.IPushService;
import com.sourcemuch.commons.push.core.config.PushConfig;
import com.sourcemuch.commons.push.core.impl.PushService;


@Configuration
@ConditionalOnProperty(prefix = "sourcemuch.commons.push", name="enabled", havingValue = "true")
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
	@ConfigurationProperties(prefix = "sourcemuch.commons.push")
	public PushConfig pushConfig() {
		return new PushConfig();
	}

}
