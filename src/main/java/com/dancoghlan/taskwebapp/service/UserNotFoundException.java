package com.dancoghlan.taskwebapp.service;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String exception) {
        super(exception);
    }

    public UserNotFoundException(String exception, Throwable throwable) {
        super(exception, throwable);
    }

}
