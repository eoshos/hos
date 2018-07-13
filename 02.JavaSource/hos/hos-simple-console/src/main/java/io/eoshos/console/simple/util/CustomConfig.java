package io.eoshos.console.simple.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:custom-config.properties")
@ConfigurationProperties(prefix = "reward")
public class CustomConfig {
	
	private int numberEnroll;
	
	private int numberInvite;

	public int getNumberEnroll() {
		return numberEnroll;
	}

	public void setNumberEnroll(int numberEnroll) {
		this.numberEnroll = numberEnroll;
	}

	public int getNumberInvite() {
		return numberInvite;
	}

	public void setNumberInvite(int numberInvite) {
		this.numberInvite = numberInvite;
	}
	
	
}
