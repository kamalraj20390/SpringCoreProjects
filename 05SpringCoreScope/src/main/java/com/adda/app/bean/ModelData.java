package com.adda.app.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelData {
	@Value("TEST")
	private String Model;

	public ModelData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}

	@Override
	public String toString() {
		return "ModelData [Model=" + Model + "]";
	}
	
	

}
