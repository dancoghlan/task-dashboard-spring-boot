package com.dancoghlan.taskwebapp.service;

import com.dancoghlan.taskwebapp.entity.Task;

import java.util.List;

public interface TaskService extends PersitenceService<Task> {

    List<Task> getAllForUserId(long userId);
}
