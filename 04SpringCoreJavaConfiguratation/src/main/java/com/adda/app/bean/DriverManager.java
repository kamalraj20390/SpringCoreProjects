package com.adda.app.bean;

public class DriverManager {
	private String driverName;
	private String url;
	public DriverManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "DriverManager [driverName=" + driverName + ", url=" + url + "]";
	}
	

}
