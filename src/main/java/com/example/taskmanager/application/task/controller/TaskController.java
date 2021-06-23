package com.example.taskmanager.application.task.controller;

import com.example.taskmanager.application.task.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * @author donath.peter@gmail.com
 */

public interface TaskController {

  /**
   * Retrieves all tasks for a User
   *
   * @param userId   userId
   * @param pageable pageable
   * @return Page of {@link TaskDto}
   */
  ResponseEntity<Page<TaskDto>> getUserTasks(Long userId, Pageable pageable);

  /**
   * Retrieves one task for a User
   *
   * @param userId userId
   * @param taskId taskId
   * @return One {@link TaskDto} if exists
   */
  ResponseEntity<TaskDto> getUserTask(Long userId, Long taskId);

  /**
   * Adds one task for a User
   *
   * @param userId  userId
   * @param taskDto taskDto
   * @return Added {@link TaskDto}
   */
  ResponseEntity<TaskDto> addTask(Long userId, TaskDto taskDto);

  /**
   * Updates a task for a User
   *
   * @param userId  userId
   * @param taskId  taskId
   * @param taskDto taskDto
   * @return Updated {@link TaskDto}
   */
  ResponseEntity<TaskDto> updateTask(Long userId, Long taskId, TaskDto taskDto);

  /**
   * Updates a task for a User
   *
   * @param userId userId
   * @param taskId taskId
   * @return HttpStatus OK if it was successful
   */
  ResponseEntity<Void> deleteTask(Long userId, Long taskId);

}
