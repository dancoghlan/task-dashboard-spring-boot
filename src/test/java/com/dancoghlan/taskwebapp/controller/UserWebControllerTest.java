package com.dancoghlan.taskwebapp.controller;

import com.dancoghlan.taskwebapp.TodoListServiceApplication;
import com.dancoghlan.taskwebapp.security.WithMockCustomUser;
import com.dancoghlan.taskwebapp.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TodoListServiceApplication.class})
@AutoConfigureMockMvc
public class UserWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    @WithAnonymousUser
    public void test_add_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/user/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockCustomUser
    public void test_add_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(get("/app/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-user"));
    }

    @Test
    @WithAnonymousUser
    public void test_list_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/user/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockCustomUser
    public void test_list_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(get("/app/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-users"));
    }

    @Test
    @WithAnonymousUser
    public void test_delete_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/user/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithAnonymousUser
    public void test_update_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/user/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

}