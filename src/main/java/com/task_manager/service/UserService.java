package com.task_manager.service;

import com.task_manager.model.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
}