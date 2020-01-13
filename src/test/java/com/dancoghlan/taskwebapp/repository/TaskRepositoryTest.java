package com.dancoghlan.taskwebapp.repository;

import com.dancoghlan.taskwebapp.entity.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * CRUD methods already tested by Spring, only testing bespoke methods.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void test_findAll() {
        // Given
        long userId = 1;
        // When
        List<Task> results = taskRepository.findAllByUserId(userId);
        // Then
        Assert.assertEquals(2, results.size());
    }

    @Test
    public void test_findAll_invalid_user() {
        // Given
        long userId = 3;
        // When
        List<Task> results = taskRepository.findAllByUserId(userId);
        // Then
        Assert.assertEquals(0, results.size());
    }

}