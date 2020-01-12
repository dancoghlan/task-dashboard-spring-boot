package com.dancoghlan.taskwebapp.service;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String exception) {
        super(exception);
    }

    public TaskNotFoundException(String exception, Throwable throwable) {
        super(exception, throwable);
    }

}
