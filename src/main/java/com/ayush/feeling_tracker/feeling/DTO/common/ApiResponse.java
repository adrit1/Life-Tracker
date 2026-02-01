package com.ayush.feeling_tracker.feeling.DTO.common;

public class ApiResponse<T> {
	
	private String message;
	private boolean success;
	private T data;
	
	//Constructor
	public ApiResponse(){
		
	}
	public ApiResponse(boolean success,String message,T data) {
		this.success=success;
		this.message=message;
		this.data=data;
	}
	
	//Static methods
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true,message,null);
	}
	public static <T> ApiResponse<T> success(String message,T data) {
		return new ApiResponse<>(true,message,data);
	}
	public static <T> ApiResponse<T> failure(String message) {
		return new ApiResponse<>(false,message,null);
	}
	public static <T> ApiResponse<T> failure(String message,T data) {
		return new ApiResponse<>(false,message,data);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
