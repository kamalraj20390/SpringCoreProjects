package com.adda.app.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.adda.app.bean.Token;
import com.adda.app.bean.TokenService;
import com.adda.app.config.AppConfig;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
		TokenService ts1=ac.getBean("tokenService",TokenService.class);
		Token t1=ts1.getToken();
		System.out.println(ts1.hashCode()+" "+t1.hashCode()+" "+ts1);
		TokenService ts2=ac.getBean("tokenService",TokenService.class);
		Token t2=ts2.getToken();
		System.out.println(ts2.hashCode()+" "+t2.hashCode()+" "+ts2);
		TokenService ts3=ac.getBean("tokenService",TokenService.class);
		Token t3=ts3.getToken();
		System.out.println(ts3.hashCode()+" "+t3.hashCode()+" "+ts3);

	}

}
