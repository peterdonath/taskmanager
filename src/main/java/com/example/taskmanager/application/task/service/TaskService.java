package com.example.taskmanager.application.task.service;

import com.example.taskmanager.application.task.dto.TaskDto;
import com.example.taskmanager.infrastructure.exception.UserMissingException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author donath.peter@gmail.com
 */

public interface TaskService {

  /**
   * Retrieves all {@link TaskDto} for the User
   *
   * @param userId   userId
   * @param pageable pageable
   * @return Page of {@link TaskDto}
   * @throws UserMissingException exception
   */
  Page<TaskDto> getUserTasks(Long userId, Pageable pageable) throws UserMissingException;

  /**
   * Retrieves one {@link TaskDto} for the User
   *
   * @param userId userId
   * @param taskId taskId
   * @return Optional of {@link TaskDto}
   */
  Optional<TaskDto> getUserTask(Long userId, Long taskId);

  /**
   * Persists one {@link TaskDto} for the User
   *
   * @param userId  userId
   * @param taskDto taskDto
   * @return Optional of saved {@link TaskDto}
   */
  Optional<TaskDto> addTask(Long userId, TaskDto taskDto);

  /**
   * Updates one {@link TaskDto} for the User
   *
   * @param userId  userId
   * @param taskId  taskId
   * @param taskDto taskDto
   * @return Optional of updated {@link TaskDto}
   */
  Optional<TaskDto> updateTask(Long userId, Long taskId, TaskDto taskDto);

  /**
   * Deletes one {@link TaskDto} for the User
   *
   * @param userId userId
   * @param taskId taskId
   * @return True if the deactivation was successful
   */
  boolean deleteTask(Long userId, Long taskId);
}
