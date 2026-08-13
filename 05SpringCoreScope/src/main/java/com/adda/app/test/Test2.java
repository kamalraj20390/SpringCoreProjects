package com.adda.app.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.adda.app.bean.Process;
import com.adda.app.config.AppConfig;

public class Test2 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
		Process p=ac.getBean("process",Process.class);
		System.out.println(p);
	}
}
