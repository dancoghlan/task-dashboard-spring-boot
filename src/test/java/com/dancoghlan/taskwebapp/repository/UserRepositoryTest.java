package com.dancoghlan.taskwebapp.repository;

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
    public void test() {
        // Given
        String username = "basic_user";
        User user = userRepository.findByUsername(username);
        // Then
        Assert.assertNotNull(user);
    }
}