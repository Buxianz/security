package com.rbi.security.exception;

//数据重复异常
public class RepeatException extends RuntimeException{
    public RepeatException() {
        super();
    }

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}