package com.example.taskmanager.application.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

import com.example.taskmanager.application.task.dto.TaskDto;
import com.example.taskmanager.application.task.service.TaskServiceImpl;
import com.example.taskmanager.domain.shared.Status;
import com.example.taskmanager.domain.task.Task;
import com.example.taskmanager.domain.task.TaskRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author donath.peter@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

  @Mock
  TaskRepository taskRepository;

  @InjectMocks
  TaskServiceImpl taskService;

  @Test
  public void getUserTasksTest() {
    List<Task> taskList = Arrays.asList(this.generateTask(1L), this.generateTask(2L));
    Pageable pageable = PageRequest.of(0, 20);
    Page<Task> taskPage = new PageImpl<>(taskList, pageable, 2);

    when(taskRepository.findAllByUserIdAndActiveFlag(any(), anyBoolean(), any()))
        .thenReturn(taskPage);

    Page<TaskDto> result = taskService.getUserTasks(1L, pageable);

    assertTrue(result.hasContent());
    assertEquals(2, result.getContent().size());
  }

  @Test
  public void getUserTaskMissingTest() {
    when(taskRepository.findByUserIdAndIdAndActiveFlag(any(), any(), anyBoolean())).thenReturn(Optional.empty());

    Optional<TaskDto> result = taskService.getUserTask(1L, 2L);

    assertTrue(result.isEmpty());
  }

  @Test
  public void getUserTaskTest() {
    Task task = this.generateTask(2L);
    when(taskRepository.findByUserIdAndIdAndActiveFlag(any(), any(), anyBoolean())).thenReturn(Optional.of(task));

    Optional<TaskDto> result = taskService.getUserTask(2L, 2L);

    assertTrue(result.isPresent());
  }

  @Test
  public void addTaskOverdueTest() {
    TaskDto taskDto = this.generateTaskDto(1L, LocalDateTime.now().minus(10, ChronoUnit.MINUTES));

    when(taskRepository.save(any(Task.class)))
        .then(inp -> inp.getArgument(0));

    Optional<TaskDto> result = taskService.addTask(1L, taskDto);

    assertTrue(result.isPresent());
    assertEquals(Status.DONE, result.get().getStatus());
    assertEquals(taskDto.getName(), result.get().getName());
    assertEquals(taskDto.getDescription(), result.get().getDescription());
    assertEquals(taskDto.getDateTime(), result.get().getDateTime());
  }

  @Test
  public void addTaskIncompleteTest() throws IllegalAccessException {
    TaskDto taskDto = this.generateTaskDto(1L, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
    FieldUtils.writeField(taskDto, "name", null, true);

    Optional<TaskDto> result = taskService.addTask(1L, taskDto);

    assertTrue(result.isEmpty());
  }

  @Test
  public void addTaskTest() {
    TaskDto taskDto = this.generateTaskDto(1L, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));

    when(taskRepository.save(any(Task.class)))
        .then(inp -> inp.getArgument(0));

    Optional<TaskDto> result = taskService.addTask(1L, taskDto);

    assertTrue(result.isPresent());
    assertEquals(Status.PENDING, result.get().getStatus());
    assertEquals(taskDto.getName(), result.get().getName());
    assertEquals(taskDto.getDescription(), result.get().getDescription());
    assertEquals(taskDto.getDateTime(), result.get().getDateTime());
  }

  @Test
  public void updateTaskMissingTest() {
    TaskDto taskDto = this.generateTaskDto(2L, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
    when(taskRepository.findById(any())).thenReturn(Optional.empty());

    Optional<TaskDto> result = taskService.updateTask(1L, 2L, taskDto);

    assertTrue(result.isEmpty());
  }

  @Test
  public void updateTaskInactiveTest() throws IllegalAccessException {
    Task origiTask = this.generateTask(2L);
    FieldUtils.writeField(origiTask, "activeFlag", false, true);
    TaskDto updateTaskDto = this.generateTaskDto(2L, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
    when(taskRepository.findById(any())).thenReturn(Optional.of(origiTask));

    Optional<TaskDto> result = taskService.updateTask(1L, 2L, updateTaskDto);

    assertTrue(result.isEmpty());
  }

  @Test
  public void updateTaskTest() throws IllegalAccessException {
    Task origiTask = this.generateTask(2L);
    TaskDto updateTaskDto = this.generateTaskDto(3L, LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
    when(taskRepository.findById(any())).thenReturn(Optional.of(origiTask));

    Optional<TaskDto> result = taskService.updateTask(2L, 2L, updateTaskDto);

    assertTrue(result.isPresent());
    assertEquals(updateTaskDto.getName(), result.get().getName());
    assertEquals(updateTaskDto.getDescription(), result.get().getDescription());
    assertEquals(updateTaskDto.getDateTime(), result.get().getDateTime());
  }

  @Test
  public void deleteTaskMissingTest() {
    when(taskRepository.findById(any())).thenReturn(Optional.empty());

    boolean result = taskService.deleteTask(1L, 2L);

    assertFalse(result);
  }

  @Test
  public void deleteTaskInactiveTest() throws IllegalAccessException {
    Task inactiveTask = this.generateTask(2L);
    FieldUtils.writeField(inactiveTask, "activeFlag", false, true);
    when(taskRepository.findById(any())).thenReturn(Optional.of(inactiveTask));

    boolean result = taskService.deleteTask(1L, 2L);

    assertFalse(result);
  }

  @Test
  public void deleteTaskTest() {
    Task activeTask = this.generateTask(2L);
    when(taskRepository.findById(any())).thenReturn(Optional.of(activeTask));

    boolean result = taskService.deleteTask(2L, 2L);

    assertTrue(result);
  }

  private Task generateTask(Long taskId) {
    return Task.builder()
        .id(taskId)
        .userId(taskId)
        .name("task_name".concat(taskId.toString()))
        .description("description".concat(taskId.toString()))
        .dateTime(LocalDateTime.now().plus(taskId, ChronoUnit.MINUTES))
        .build();
  }

  private TaskDto generateTaskDto(Long objId, LocalDateTime dateTime) {
    return TaskDto.builder()
        .name("task_name".concat(objId.toString()))
        .description("description".concat(objId.toString()))
        .dateTime(dateTime)
        .build();
  }
}
