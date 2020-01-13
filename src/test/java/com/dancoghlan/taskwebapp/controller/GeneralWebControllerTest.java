package com.dancoghlan.taskwebapp.controller;

import com.dancoghlan.taskwebapp.TodoListServiceApplication;
import com.dancoghlan.taskwebapp.config.TestConfig;
import com.dancoghlan.taskwebapp.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TodoListServiceApplication.class, TestConfig.class})
@AutoConfigureMockMvc
public class GeneralWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void test_home_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockCustomUser
    public void test_home_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(get("/app/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithAnonymousUser
    public void test_login_page_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithAnonymousUser
    public void test_logout_page_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockCustomUser
    public void test_logout_page_accessible_to_logged_in_user() throws Exception {
        this.mockMvc.perform(get("/app/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("logout"));
    }

    @Test
    @WithMockCustomUser
    public void test_access_denied_page_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("access-denied"));
    }

}