package com.taskmanagement.tasks.service;

import com.taskmanagement.tasks.dto.TaskDTO;
import com.taskmanagement.tasks.dto.TaskRequestDTO;
import com.taskmanagement.tasks.entity.Task;
import com.taskmanagement.tasks.entity.TaskStatus;
import com.taskmanagement.tasks.exception.TaskNotFoundException;
import com.taskmanagement.tasks.mapper.TaskMapper;
import com.taskmanagement.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ValidRequest_ReturnsTaskDTO() {
        TaskRequestDTO request = new TaskRequestDTO("Test", "Description", LocalDate.now(), TaskStatus.PENDING);
        Task task = new Task();
        TaskDTO expected = new TaskDTO(1L, "Test", "Description", LocalDate.now(), TaskStatus.PENDING);

        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(expected);

        TaskDTO result = taskService.createTask(request);

        assertEquals(expected, result);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_NonExistingId_ThrowsException() {
        Long id = 1L;
        TaskRequestDTO request = new TaskRequestDTO("Test", "Desc", LocalDate.now(), TaskStatus.PENDING);

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(id, request));
    }

    @Test
    void getTaskById_ExistingId_ReturnsTaskDTO() {
        Long taskId = 1L;
        Task task = new Task(taskId, "Test", "Desc", LocalDate.now(), TaskStatus.PENDING);
        TaskDTO expected = new TaskDTO(taskId, "Test", "Desc", LocalDate.now(), TaskStatus.PENDING);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(expected);

        TaskDTO result = taskService.getTaskById(taskId);

        assertEquals(expected, result);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getTaskById_NonExistingId_ThrowsException() {
        Long taskId = 999L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
    }
}
