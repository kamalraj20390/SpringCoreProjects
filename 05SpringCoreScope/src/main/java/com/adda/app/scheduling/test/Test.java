package com.adda.app.scheduling.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.adda.app.scheduling.config.AppConfig;
import com.adda.app.scheduling.service.ProductReportService;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac =new AnnotationConfigApplicationContext(AppConfig.class);
		ProductReportService ps=ac.getBean(ProductReportService.class);
	}

}
