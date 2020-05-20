package com.rbi.security.exception;
//数据不存在异常
public class NonExistentException extends RuntimeException{
    public NonExistentException() {
        super();
    }

    public NonExistentException(String message) {
        super(message);
    }

    public NonExistentException(String message, Throwable cause) {
        super(message, cause);
    }
}