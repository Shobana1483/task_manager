package com.task_manager.controller;

import com.task_manager.model.Task;
import com.task_manager.repository.TaskRepository;
import com.task_manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    // View tasks (all or filter by status)
    @GetMapping
    public String viewTasks(@RequestParam(required = false) String filter, Model model) {
        List<Task> tasks;

        if ("completed".equalsIgnoreCase(filter)) {
            tasks = taskService.getTasksByStatus(true);
        } else if ("pending".equalsIgnoreCase(filter)) {
            tasks = taskService.getTasksByStatus(false);
        } else {
            tasks = taskService.getAllTasks();
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
        return "tasks"; // tasks.html
    }

    // Add task
    @PostMapping("/add")
    public String addTask(@RequestParam String title,
                          @RequestParam(required = false) String description,
                          @RequestParam(required = false) String dueDate,
                          @RequestParam(required = false) String priority) {

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);

        if (dueDate != null && !dueDate.isEmpty()) {
            task.setDueDate(LocalDate.parse(dueDate));
        }

        if (priority != null && !priority.isEmpty()) {
            task.setPriority(priority.toUpperCase());
        }

        taskService.createTask(task);
        return "redirect:/tasks";
    }

    // Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    // Complete task
    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null && !task.isCompleted()) {
            task.setCompleted(true);
            taskService.updateTask(task);
        }
        return "redirect:/tasks";
    }

    // ✅ FIXED: edit URL
    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task ID:" + id));
        model.addAttribute("task", task);
        return "editTask"; // loads editTask.html
    }

    // ✅ FIXED: update URL
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task ID:" + id));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setPriority(updatedTask.getPriority());

        taskRepository.save(task);
        return "redirect:/tasks";
    }
}
