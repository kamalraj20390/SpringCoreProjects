package com.adda.app.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.adda.app.bean.HandlerInfo;
import com.adda.app.bean.MyController;
import com.adda.app.config.MyAppConfig;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(MyAppConfig.class);
		/*
		 * ac.scan("com.adda.app"); ac.refresh();
		 */
		MyController mob=ac.getBean("myController",MyController.class);
		System.out.println(mob);

	}

}
