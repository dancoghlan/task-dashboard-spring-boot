package com.dancoghlan.taskwebapp.service;

import com.dancoghlan.taskwebapp.entity.Task;
import com.dancoghlan.taskwebapp.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Test
    @DirtiesContext
    public void test_add() {
        // Given
        final String name = "Clan room";
        final String description = "Start cleaning today";
        final User user = new User("username", "password");
        userService.add(user);
        Task task = new Task(name, description, user);
        // When
        Task addedTask = taskService.add(task);
        // Then
        Optional<Task> result = taskService.getById(addedTask.getId());
        Assert.assertTrue(result.isPresent());
        doAssertions(result.get(), name, description, false, user);
    }

    @Test
    @DirtiesContext
    public void test_getById_found() {
        // When
        Optional<Task> result = taskService.getById(1);
        // Then
        Assert.assertTrue(result.isPresent());
    }

    @Test
    @DirtiesContext
    public void test_getById_not_found() {
        // When
        Optional<Task> result = taskService.getById(100);
        // Then
        Assert.assertFalse(result.isPresent());
    }

    @Test
    @DirtiesContext
    public void test_getAll() {
        // When
        List<Task> results = taskService.getAll();
        // Then
        Assert.assertEquals(3, results.size());
    }

    @Test
    @DirtiesContext
    public void test_getAllForUser() {
        // Given
        final long userId = 1;
        // When
        List<Task> results = taskService.getAllForUserId(userId);
        // Then
        Assert.assertEquals(2, results.size());
    }

    @Test
    @DirtiesContext
    public void test_remove() {
        // Given
        Assert.assertTrue(taskService.getById(1).isPresent());
        // When
        taskService.remove(1);
        // Then
        Assert.assertFalse(taskService.getById(1).isPresent());
    }


    private void doAssertions(Task task, String name, String description, boolean completed, User user) {
        Assert.assertNotNull(task);
        Assert.assertEquals(name, task.getName());
        Assert.assertEquals(description, task.getDescription());
        Assert.assertEquals(completed, task.isCompleted());
        Assert.assertEquals(user.getId(), task.getUser().getId());
    }

}