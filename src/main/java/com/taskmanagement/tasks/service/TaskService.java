package com.taskmanagement.tasks.service;

import com.taskmanagement.tasks.dto.TaskDTO;
import com.taskmanagement.tasks.dto.TaskRequestDTO;
import com.taskmanagement.tasks.entity.Task;
import com.taskmanagement.tasks.exception.TaskNotFoundException;
import com.taskmanagement.tasks.mapper.TaskMapper;
import com.taskmanagement.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.toEntity(taskRequestDTO);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        taskMapper.updateEntity(taskRequestDTO, task);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
