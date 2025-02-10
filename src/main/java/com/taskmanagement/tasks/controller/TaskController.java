package com.taskmanagement.tasks.controller;

import com.taskmanagement.tasks.dto.TaskDTO;
import com.taskmanagement.tasks.dto.TaskRequestDTO;
import com.taskmanagement.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return taskService.createTask(taskRequestDTO);
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return taskService.updateTask(id, taskRequestDTO);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}