package com.taskmanagement.tasks.dto;

import com.taskmanagement.tasks.entity.TaskStatus;
import lombok.Builder;
import lombok.Data;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskDTO {
    public Long id;
    public String title;
    public String description;
    public LocalDate dueDate;
    public TaskStatus status;
}
