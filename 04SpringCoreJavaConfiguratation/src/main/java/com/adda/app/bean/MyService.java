package com.adda.app.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MyService {
	@Autowired
	@Qualifier("conObj1")
	private DriverManager man;

	public MyService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DriverManager getMan() {
		return man;
	}

	public void setMan(DriverManager man) {
		this.man = man;
	}

	@Override
	public String toString() {
		return "MyService [man=" + man + "]";
	}
	
}
