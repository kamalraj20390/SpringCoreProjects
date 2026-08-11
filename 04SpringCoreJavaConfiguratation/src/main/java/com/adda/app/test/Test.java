package com.adda.app.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.adda.app.bean.MyService;
import com.adda.app.config.MyAppConfig;

public class Test {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(MyAppConfig.class);
		MyService ms=ac.getBean("myService",MyService.class);
		System.out.println(ms);
	}

}
