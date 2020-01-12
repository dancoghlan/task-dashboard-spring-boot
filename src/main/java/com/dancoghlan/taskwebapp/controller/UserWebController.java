package com.dancoghlan.taskwebapp.controller;

import com.dancoghlan.taskwebapp.entity.User;
import com.dancoghlan.taskwebapp.service.UserNotFoundException;
import com.dancoghlan.taskwebapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.String.format;

@Controller
@RequestMapping("/app/user")
public class UserWebController {
    public static final Logger logger = LoggerFactory.getLogger(UserWebController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addUserPage(User user) {
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        String userName = user.getUsername();
        User existingUser = userService.getByUsername(userName);
        if (existingUser != null) {
            logger.warn("A user already exists with username [{}]", userName);
            return "add-user";
        }
        userService.add(user);
        return "redirect:list";
    }

    @RequestMapping("/list")
    public String userListPage(Model model) {
        model.addAttribute("users", userService.getAll());
        return "list-users";
    }

    @GetMapping("/update/{id}")
    public String updateUserPage(@PathVariable("id") long id, Model model) {
        User user = userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException(format("Could not find user with id [%s]", id)));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-users";
        }
        userService.update(user);
        model.addAttribute("users", userService.getAll());
        return "list-users";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.remove(id);
        model.addAttribute("users", userService.getAll());
        return "list-users";
    }

}
