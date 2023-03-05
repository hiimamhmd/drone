package com.musala.drone.util;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class DataWrapper {

	private String message;
	private Object data;
	private Boolean success;
	private int dataCount;
	
}
