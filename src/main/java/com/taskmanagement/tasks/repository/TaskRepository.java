package com.taskmanagement.tasks.repository;

import com.taskmanagement.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
