package com.adda.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.adda.app.bean.DriverManager;

@Configuration
@ComponentScan("com.adda.app")
public class MyAppConfig {
	@Bean
	//1 method = 1 Object
	//@Primary
	public DriverManager conObj() {
		DriverManager dm=new DriverManager();
		dm.setDriverName("ORACLE");
		dm.setUrl("LOCALHOST");
		return dm;
	}
	@Bean
	//1 method = 1 Object
	public DriverManager conObj1() {
		DriverManager dm=new DriverManager();
		dm.setDriverName("ORACLE1");
		dm.setUrl("LOCALHOST1");
		return dm;
	}

}
