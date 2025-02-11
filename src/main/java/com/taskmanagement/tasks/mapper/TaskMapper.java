package com.taskmanagement.tasks.mapper;

import com.taskmanagement.tasks.dto.TaskDTO;
import com.taskmanagement.tasks.dto.TaskRequestDTO;
import com.taskmanagement.tasks.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .build();
    }

    public Task toEntity(TaskRequestDTO taskRequestDTO) {
        return Task.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .dueDate(taskRequestDTO.getDueDate())
                .status(taskRequestDTO.getStatus())
                .build();
    }

    public void updateEntity(TaskRequestDTO dto, Task entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setDueDate(dto.getDueDate());
        entity.setStatus(dto.getStatus());
    }
}