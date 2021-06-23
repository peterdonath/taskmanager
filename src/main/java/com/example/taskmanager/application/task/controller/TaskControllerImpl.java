package com.example.taskmanager.application.task.controller;

import com.example.taskmanager.application.task.dto.TaskDto;
import com.example.taskmanager.application.task.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author donath.peter@gmail.com
 */

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskControllerImpl implements TaskController {

  private final TaskServiceImpl taskService;

  @Value(value = "${taskmanager.default-page-size}")
  private int defaultPageSize;

  @Override
  @GetMapping("/api/user/{userId}/task")
  public ResponseEntity<Page<TaskDto>> getUserTasks(@PathVariable Long userId, Pageable pageable) {
    return ResponseEntity.ok(
        taskService.getUserTasks(userId, pageable != null ? pageable : PageRequest.of(0, defaultPageSize)));
  }

  @Override
  @GetMapping("/api/user/{userId}/task/{taskId}")
  public ResponseEntity<TaskDto> getUserTask(@PathVariable Long userId, @PathVariable Long taskId) {
    return ResponseEntity.of(taskService.getUserTask(userId, taskId));
  }

  @Override
  @PostMapping("api/user/{userId}/task")
  public ResponseEntity<TaskDto> addTask(@PathVariable Long userId, @RequestBody TaskDto taskDto) {
    return ResponseEntity.of(taskService.addTask(userId, taskDto));
  }

  @Override
  @PutMapping("/api/user/{userId}/task/{taskId}")
  public ResponseEntity<TaskDto> updateTask(@PathVariable Long userId, @PathVariable Long taskId,
      @RequestBody TaskDto taskDto) {
    return ResponseEntity.of(taskService.updateTask(userId, taskId, taskDto));
  }

  @Override
  @DeleteMapping("/api/user/{userId}/task/{taskId}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
    if (taskService.deleteTask(userId, taskId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
  }
}
