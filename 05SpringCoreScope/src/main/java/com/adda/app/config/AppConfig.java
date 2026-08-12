package com.adda.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.adda.app.bean.Token;

@ComponentScan("com.adda.app")
//@Configuration
public class AppConfig {
	//@Bean
	//@Scope("singleton")
	public Token token() {
		return new Token();
	}
}
