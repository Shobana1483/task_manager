package com.task_manager.repository;

import com.task_manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Get all tasks filtered by completion status
    List<Task> findByCompleted(boolean completed);
}