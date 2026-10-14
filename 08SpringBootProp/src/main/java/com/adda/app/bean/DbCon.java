package com.adda.app.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@ConfigurationProperties(prefix = "my.db")
@Data
//@Profile({"prod","qa"})
//@Profile("default")
public class DbCon {
	//@Value("${my.db.driver}")
	private String driver;
	//@Value("${my.db.url}")
	private String url;
	//@Value("${my.db.user}")
	private String user;
	//@Value("${my.db.password}")
	private String password;	
}
