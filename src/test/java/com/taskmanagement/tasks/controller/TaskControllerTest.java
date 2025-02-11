package com.taskmanagement.tasks.controller;

import com.taskmanagement.tasks.dto.TaskDTO;
import com.taskmanagement.tasks.dto.TaskRequestDTO;
import com.taskmanagement.tasks.entity.TaskStatus;
import com.taskmanagement.tasks.exception.TaskNotFoundException;
import com.taskmanagement.tasks.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_ValidInput_ReturnsCreated() throws Exception {
        TaskRequestDTO request = new TaskRequestDTO("Test", "Desc", LocalDate.now(), TaskStatus.PENDING);
        TaskDTO response = new TaskDTO(1L, "Test", "Desc", LocalDate.now(), TaskStatus.PENDING);

        when(taskService.createTask(any(TaskRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTaskById_ValidId_ReturnsTask() throws Exception {
        Long taskId = 1L;
        TaskDTO mockDto = new TaskDTO(taskId, "Test", "Desc", LocalDate.now(), TaskStatus.PENDING);

        when(taskService.getTaskById(taskId)).thenReturn(mockDto);

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId));
    }

    @Test
    void getTaskById_InvalidId_ReturnsNotFound() throws Exception {
        Long taskId = 999L;
        when(taskService.getTaskById(taskId))
                .thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(status().isNotFound());
    }
}
