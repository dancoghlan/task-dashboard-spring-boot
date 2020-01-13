package com.dancoghlan.taskwebapp.service;

import com.dancoghlan.taskwebapp.entity.Role;
import com.dancoghlan.taskwebapp.entity.Task;
import com.dancoghlan.taskwebapp.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    @DirtiesContext
    public void test_loadUserByUsername_found_user() {
        // Given - Username already in DB
        Optional<User> result = userService.getById(1);
        User user = result.orElseThrow(() -> new UserNotFoundException("User not found"));
        String username = user.getUsername();
        // When
        UserDetails userDetails = userService.loadUserByUsername(username);
        // Then
        User userResult = new User(userDetails);
        compareUser(userResult, username, user.getRoles(), null);
    }

    @Test
    @DirtiesContext
    public void test_loadUserByUsername_no_user() {
        final String username = "new_user";
        UserDetails userDetails = userService.loadUserByUsername(username);
        Assert.assertNull(userDetails);
    }

    @Test
    @DirtiesContext
    public void test_add() {
        // Given
        final String username = "new_user";
        final String password = "pass";
        final List<Role> roles = Arrays.asList(new Role("USER"), new Role("ADMIN"));
        User user = new User(username, password, roles);
        // When
        userService.add(user);
        // Then
        User result = userService.getByUsername(username);
        compareUser(user, username, roles, null);
    }

    @Test (expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void test_add_duplicate_username() {
        Optional<User> result = userService.getById(1);
        // Given - Username already in DB
        final String username = result.orElseThrow(() -> new UserNotFoundException("User not found")).getUsername();
        final String password = "pass";
        final List<Role> roles = Arrays.asList(new Role("USER"));
        User user = new User(username, password, roles);
        // When
        userService.add(user);
        // Then - throw exception
    }

    @Test
    @DirtiesContext
    public void test_getById_found() {
        // When
        Optional<User> result = userService.getById(1);
        // Then
        Assert.assertTrue(result.isPresent());
    }

    @Test
    @DirtiesContext
    public void test_getById_not_found() {
        // When
        Optional<User> result = userService.getById(100);
        // Then
        Assert.assertFalse(result.isPresent());
    }

    @Test
    @DirtiesContext
    public void test_getAll() {
        // When
        List<User> results = userService.getAll();
        // Then
        Assert.assertEquals(2, results.size());
    }

    @Test
    @DirtiesContext
    public void test_remove() {
        // Given
        final String username = "new_user";
        final String password = "pass";
        final List<Role> roles = Arrays.asList(new Role("USER"));
        User user = new User(username, password, roles);
        long addedUserId = userService.add(user).getId();
        // When
        userService.remove(addedUserId);
        // Then
        Assert.assertFalse(userService.getById(addedUserId).isPresent());
    }

    private void compareUser(User user, String username, List<Role> roles, List<Task> tasks) {
        Assert.assertNotNull(user);
        Assert.assertEquals(username, user.getUsername());
        Assert.assertNotNull(user.getPassword());
        Assert.assertEquals(roles.stream().map(Role::getName).collect(toList()),
                user.getRoles().stream().map(Role::getName).collect(toList()));
        Assert.assertEquals(tasks, user.getTasks());
    }

}