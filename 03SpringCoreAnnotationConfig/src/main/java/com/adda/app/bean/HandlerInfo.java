package com.adda.app.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component("hob")
public class HandlerInfo {
	//@Value("101")
	@Value("${com.adda.app.hid}")
	private Integer hid;
	@Value("${com.adda-app.resolverCode}")
	private String resolverCode;
	@Value("${com-adda-app-pathToStore}")
	private String pathToStore;
	public HandlerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getResolverCode() {
		return resolverCode;
	}
	public void setResolverCode(String resolverCode) {
		this.resolverCode = resolverCode;
	}
	public String getPathToStore() {
		return pathToStore;
	}
	public void setPathToStore(String pathToStore) {
		this.pathToStore = pathToStore;
	}
	@Override
	public String toString() {
		return "HandlerInfo [hid=" + hid + ", resolverCode=" + resolverCode + ", pathToStore=" + pathToStore + "]";
	}
	

}
