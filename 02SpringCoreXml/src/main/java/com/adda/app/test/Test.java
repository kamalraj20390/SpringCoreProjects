package com.adda.app.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.adda.app.bean.OracleCon;

public class Test {

	public static void main(String[] args) {
		ApplicationContext ac=new ClassPathXmlApplicationContext("config.xml");
		OracleCon con=ac.getBean("con",OracleCon.class);
		System.out.println(con);

	}

}
