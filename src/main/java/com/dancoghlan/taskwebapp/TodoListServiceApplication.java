package com.dancoghlan.taskwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TodoListServiceApplication implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(TodoListServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TodoListServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }

    /**
     * Helper method to get hashed value of password to store in DB for the sake of testing.
     *
     * See file data.sql for encrypted value used in testing.
     */
    private void encryptPassword(String password) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        logger.info("Encryped password: [{}]", encryptedPassword);
    }

}
