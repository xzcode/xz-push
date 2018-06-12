package com.sourcemuch.commons.push.core.config;

public class PushConfig {
	
	private String packages;
	
	private String ios_appsecret;
	
	private String android_appsecret;
	
	private Integer retries;
	
	private String env;
	
	private boolean enabled;

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public String getIos_appsecret() {
		return ios_appsecret;
	}

	public void setIos_appsecret(String ios_appsecret) {
		this.ios_appsecret = ios_appsecret;
	}

	public String getAndroid_appsecret() {
		return android_appsecret;
	}

	public void setAndroid_appsecret(String android_appsecret) {
		this.android_appsecret = android_appsecret;
	}

	public Integer getRetries() {
		return retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
