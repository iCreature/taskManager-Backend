package com.taskmanagement.tasks.dto;

import com.taskmanagement.tasks.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskRequestDTO {
    @NotBlank(message = "Title is mandatory")
    public String title;

    public String description;

    @NotNull(message = "Due date is mandatory")
    @FutureOrPresent(message = "Due date must be in the present or future")
    public LocalDate dueDate;

    @NotNull(message = "Status is mandatory")
    public TaskStatus status;
}