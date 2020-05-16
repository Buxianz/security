package com.rbi.security.tool;

import java.io.Serializable;


public class ResponseModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;
    private String message;
    private String token;
    private T data;

    public static <T> ResponseModel<T> build(String status, String message, String token, T data) {
        return new ResponseModel<T>().setStatus(status).setMessage(message).setToken(token).setData(data);
    }

    public static <T> ResponseModel<T> build(String status, String message, T data) {
        return new ResponseModel<T>().setStatus(status).setMessage(message).setData(data);
    }
    
    public static <E> ResponseModel<E> build(String status, String message, String token) {
        return new ResponseModel<E>().setStatus(status).setMessage(message).setToken(token);
    }

    public static <E> ResponseModel<E> build(String status, String message) {
        return new ResponseModel<E>().setStatus(status).setMessage(message);
    }

    public ResponseModel() {
    }

    public String getStatus() {
        return status;
    }

    public ResponseModel<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseModel<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }
    
    public String getToken() {
		return token;
	}

	public ResponseModel<T> setToken(String token) {
		this.token = token;
		return this;
	}

	public ResponseModel<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
	public String toString() {
		return "{\"status\":\"" + status + "\",\"message\":\"" + message  + "\",\"token\":\"" + token + "\",\"data\":" + data + "}";
	}
}
