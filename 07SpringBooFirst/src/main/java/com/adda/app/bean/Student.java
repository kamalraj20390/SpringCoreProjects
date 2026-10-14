package com.adda.app.bean;

import org.springframework.stereotype.Component;

//@Component
public class Student {
	private Integer stdId;
	private String stdName;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(Integer stdId, String stdName) {
		super();
		this.stdId = stdId;
		this.stdName = stdName;
	}
	public Integer getStdId() {
		return stdId;
	}
	public void setStdId(Integer stdId) {
		this.stdId = stdId;
	}
	public String getStdName() {
		return stdName;
	}
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}
	@Override
	public String toString() {
		return "Student [stdId=" + stdId + ", stdName=" + stdName + "]";
	}
	
}
