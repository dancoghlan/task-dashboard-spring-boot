package com.dancoghlan.taskwebapp.controller;

import com.dancoghlan.taskwebapp.TodoListServiceApplication;
import com.dancoghlan.taskwebapp.security.WithMockCustomUser;
import com.dancoghlan.taskwebapp.service.TaskService;
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
public class TaskWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Test
    @WithAnonymousUser
    public void test_add_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/task/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockCustomUser
    public void test_add_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(get("/app/task/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-task"));
    }

    @Test
    @WithAnonymousUser
    public void test_list_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/task/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Test
    @WithMockCustomUser
    public void test_list_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(get("/app/task/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-tasks"));
    }

    @Test
    @WithAnonymousUser
    public void test_delete_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/task/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Ignore
    @Test
    @WithMockCustomUser
    public void test_delete_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(post("/app/task/delete"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void test_update_page_user_not_accessible_to_anon_user() throws Exception {
        this.mockMvc.perform(get("/app/task/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/app/login"));
    }

    @Ignore
    @Test
    @WithMockCustomUser
    public void test_update_page_accessible_when_user_logged_in() throws Exception {
        this.mockMvc.perform(post("/app/task/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-tasks"));
    }

    @Ignore
    @Test
    @WithMockCustomUser
    public void test_add_task_post() throws Exception {
        this.mockMvc.perform(post("/app/task/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/app/task/list"));
    }

}