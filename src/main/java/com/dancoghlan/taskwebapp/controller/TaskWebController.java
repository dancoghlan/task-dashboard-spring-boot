package com.dancoghlan.taskwebapp.controller;

import com.dancoghlan.taskwebapp.entity.Task;
import com.dancoghlan.taskwebapp.entity.User;
import com.dancoghlan.taskwebapp.service.TaskNotFoundException;
import com.dancoghlan.taskwebapp.service.TaskService;
import com.dancoghlan.taskwebapp.service.UserNotFoundException;
import com.dancoghlan.taskwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.String.format;

@Controller
@RequestMapping("/app/task")
public class TaskWebController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addUserPage(Task task) {
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-task";
        }
        task.setUser(getCurrentUser());
        taskService.add(task);
        return "redirect:list";
    }

    @RequestMapping("/list")
    public String taskListPage(Model model) {
        model.addAttribute("tasks", taskService.getAllForUserId(getCurrentUser().getId()));
        return "list-tasks";
    }

    @GetMapping("/update/{id}")
    public String updateTaskPage(@PathVariable("id") long id, Model model) {
        Task task = taskService.getById(id)
                .orElseThrow(() -> new TaskNotFoundException(format("Could not find task with id [%s]", id)));
        model.addAttribute("task", task);
        return "update-task";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") long id, Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            task.setId(id);
            return "update-tasks";
        }
        User user = getCurrentUser();
        task.setUser(user);
        taskService.update(task);
        model.addAttribute("tasks", taskService.getAllForUserId(user.getId()));
        return "list-tasks";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") long id, Model model) {
        taskService.remove(id);
        model.addAttribute("tasks", taskService.getAllForUserId(getCurrentUser().getId()));
        return "list-tasks";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        if (user == null) {
            throw new UserNotFoundException(format("Could not find user with username [%s]", authentication.getName()));
        }
        return user;
    }

}
