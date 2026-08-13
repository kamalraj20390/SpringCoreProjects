package com.adda.app.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Process {
//	@Value("#{2+3}")
//	@Value("#{'Hello ABC'.length()}")
//	@Value("#{T(java.lang.Math).abs(-9)}")
	private String code;
//	@Value("#{false}")
//	@Value("#{new java.util.Random().nextInt()>5?true:false}")
//	@Value("#{new java.util.Random().nextInt() ge 5 ? true:false}")
	@Value("#{2/0}")
	private boolean isPresent;
	@Override
	public String toString() {
		return "Process [code=" + code + ", isPresent=" + isPresent + "]";
	}
	
}
