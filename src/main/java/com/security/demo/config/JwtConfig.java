package com.security.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration @ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

	private String secret;
	private String header;
	private Long expiration;
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public Long getExpiration() {
		return expiration;
	}
	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}
}
