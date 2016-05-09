package com.domain.exception;


public class ApplicationLimitReachException extends Throwable {
    public ApplicationLimitReachException(String s) {
        super(s);
    }
}
