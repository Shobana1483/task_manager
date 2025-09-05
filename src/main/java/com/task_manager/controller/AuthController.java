package com.task_manager.controller;

import com.task_manager.model.User;
import com.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Registration page
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.register(user);
        model.addAttribute("message", "Registration successful! Please login.");
        return "login";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        User user = userService.login(username, password);
        if (user != null) {
            return "redirect:/tasks?userId=" + user.getId();
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}