package com.example.taskmanager.application.maintenance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taskmanager.domain.shared.Status;
import com.example.taskmanager.domain.task.Task;
import com.example.taskmanager.domain.task.TaskRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.hibernate.annotations.ManyToAny;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author donath.peter@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class TaskMaintenanceServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskMaintenanceService taskMaintenanceService;

  @Test
  public void updateTasksTest() {
    Task task = this.generateTask(1L, Status.PENDING);

    when(taskRepository.findAllTasksBefore(any(), any())).thenReturn(Arrays.asList(1L));
    when(taskRepository.findById(any())).thenReturn(Optional.of(task));
    when(taskRepository.findAllRemovableBefore(any())).thenReturn(Collections.emptyList());
    when(taskRepository.save(any(Task.class)))
        .then(inp -> inp.getArgument(0));

    taskMaintenanceService.maintenanceTask();

    verify(taskRepository, times(1)).save(any(Task.class));
  }

  private Task generateTask(Long taskId, Status status) {
    return Task.builder()
        .id(taskId)
        .userId(taskId)
        .name("task_name".concat(taskId.toString()))
        .description("description".concat(taskId.toString()))
        .dateTime(LocalDateTime.now().plus(taskId, ChronoUnit.MINUTES))
        .status(status)
        .build();
  }
}
