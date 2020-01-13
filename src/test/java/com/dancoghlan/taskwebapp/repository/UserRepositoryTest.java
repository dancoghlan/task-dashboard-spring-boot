package com.dancoghlan.taskwebapp.repository;

import com.dancoghlan.taskwebapp.entity.Role;
import com.dancoghlan.taskwebapp.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * CRUD methods already tested by Spring, only testing bespoke methods.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_user_found() {
        // Given
        String username = "basic_user";
        // When
        User user = userRepository.findByUsername(username);
        // Then
        Assert.assertEquals("basic_user", user.getUsername());
        Assert.assertEquals("$2a$10$TjW6DQ8DZuJ9RBnJKYfc4eYB2wwHJ/K5Ni7FUIgot.Gc2jy8Jj6RS", user.getPassword());
        Assert.assertEquals(1, user.getRoles().size());
        Assert.assertEquals(2, user.getTasks().size());
        Assert.assertNotNull(user);
    }

    @Test
    public void test_no_user_found() {
        // Given
        String username = "invalid_user";
        // When
        User user = userRepository.findByUsername(username);
        // Then
        Assert.assertNull(user);
    }

}