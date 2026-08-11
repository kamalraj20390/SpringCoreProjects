package com.adda.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.adda.app")
@PropertySource("classpath:application.properties")
public class MyAppConfig {

}
