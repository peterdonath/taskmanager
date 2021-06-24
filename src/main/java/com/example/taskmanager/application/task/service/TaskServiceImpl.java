package com.example.taskmanager.application.task.service;

import com.example.taskmanager.application.task.dto.TaskDto;
import com.example.taskmanager.domain.shared.Status;
import com.example.taskmanager.domain.task.Task;
import com.example.taskmanager.domain.task.TaskRepository;
import com.example.taskmanager.infrastructure.annotation.ValidateParams;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author donath.peter@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  @ValidateParams
  public Page<TaskDto> getUserTasks(Long userId, Pageable pageable) {
    return taskRepository.findAllByUserIdAndActiveFlag(userId, true, pageable)
        .map(TaskDto::mapEntityToDto);
  }

  @Override
  @ValidateParams
  public Optional<TaskDto> getUserTask(Long userId, Long taskId) {
    return taskRepository.findByUserIdAndIdAndActiveFlag(userId, taskId, true).map(TaskDto::mapEntityToDto);
  }

  @Override
  @ValidateParams
  public Optional<TaskDto> addTask(Long userId, TaskDto taskDto) {
    if (taskDto.getName() == null) {
      return Optional.empty();
    }

    Task task = TaskDto.mapDtoToEntity(taskDto);
    task.setStatus(taskDto.getDateTime() != null
        && taskDto.getDateTime().isAfter(LocalDateTime.now())
        ? Status.PENDING : Status.DONE);
    task.setUserId(userId);

    taskRepository.save(task);

    return Optional.of(TaskDto.mapEntityToDto(task));
  }

  @Override
  @ValidateParams
  @Transactional
  public Optional<TaskDto> updateTask(Long userId, Long taskId, TaskDto taskDto) {
    Optional<Task> taskOptional = taskRepository.findById(taskId);

    if (taskOptional.isEmpty()
        || !taskOptional.get().getUserId().equals(userId)
        || !taskOptional.get().isActiveFlag()) {
      return Optional.empty();
    }

    Task task = taskOptional.get();
    task.setName(StringUtils.defaultIfBlank(taskDto.getName(), task.getName()));
    task.setDescription(StringUtils.defaultIfBlank(taskDto.getDescription(), task.getDescription()));
    task.setDateTime(ObjectUtils.defaultIfNull(taskDto.getDateTime(), task.getDateTime()));
    task.setStatus(ObjectUtils.defaultIfNull(taskDto.getStatus(), task.getStatus()));

    return Optional.of(TaskDto.mapEntityToDto(task));
  }

  @Override
  @ValidateParams
  @Transactional
  public boolean deleteTask(Long userId, Long taskId) {
    Optional<Task> taskOptional = taskRepository.findById(taskId);

    if (taskOptional.isEmpty()
        || !taskOptional.get().getUserId().equals(userId)
        || !taskOptional.get().isActiveFlag()) {
      return false;
    }

    taskOptional.get().disable();
    return true;
  }
}
