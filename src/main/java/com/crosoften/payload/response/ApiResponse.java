package com.crosoften.payload.response;


import lombok.Data;

@Data
public class ApiResponse {
	
	private Boolean success;
	private String message;
	
	
	public ApiResponse(Boolean success, String message) {
		this.message = message;
		this.success = success;
	}
	
}
