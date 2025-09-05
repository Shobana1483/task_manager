package com.task_manager.service;

import com.task_manager.model.Task;
import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    void deleteTask(Long id);

    Task updateTask(Task task);

    Task getTaskById(Long id);

    List<Task> getAllTasks();

    List<Task> getTasksByStatus(boolean completed);
}