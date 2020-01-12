package com.dancoghlan.taskwebapp.service;

import com.dancoghlan.taskwebapp.entity.Task;
import com.dancoghlan.taskwebapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TaskServiceImpl extends AbstractPersistenceService<Task> implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        super(taskRepository);
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllForUserId(long userId) {
        return taskRepository.findAllByUserId(userId);
    }

}
