package com.adda.app.scheduling.service;

import java.util.Date;
import java.util.concurrent.Delayed;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductReportService {
	//@Scheduled(fixedDelay = 2000)
	//@Scheduled(fixedRate =  2000)
	@Scheduled(cron = "0 40 12 * * *")
	public void generateReport() {
		System.out.println("Placement ADDA "+ new Date());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
