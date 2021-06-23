package com.example.taskmanager.application.maintenance;

import com.example.taskmanager.domain.shared.Status;
import com.example.taskmanager.domain.task.Task;
import com.example.taskmanager.domain.task.TaskRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author donath.peter@gmail.com
 */

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskMaintenanceService {

  private final TaskRepository taskRepository;

  @Value("${taskmanager.retention-days}")
  private long retentionTimeDays;

  /**
   * Scheduled thread to remove old tasks and change status of done ones
   */
  @Scheduled(fixedDelayString = "${taskmanager.maintenance-task-delay}",
      initialDelayString = "${random.long(${taskmanager.maintenance-task-delay})}")
  public void maintenanceTask() {

    this.moveOutdatedTasksToDone();

    this.removeOldDisabledTasks();
  }

  /**
   * Moves from pending to done status after dateTime limit passed
   */
  private void moveOutdatedTasksToDone() {
    taskRepository.findAllTasksBefore(LocalDateTime.now(), Status.PENDING)
        .forEach(this::setToDone);
  }

  /**
   * Sets status Done to the task with id
   *
   * @param taskId taskId
   */
  private void setToDone(Long taskId) {
    Optional<Task> taskOptional = taskRepository.findById(taskId);

    taskOptional.ifPresent(task -> {
      task.setStatus(Status.DONE);
      taskRepository.save(task);
    });

    log.info("Task updated: {}", taskOptional.get());
  }

  /**
   * Deletes Old, Disabled tasks
   */
  private void removeOldDisabledTasks() {
    LocalDateTime endDate = LocalDateTime.now().minus(retentionTimeDays, ChronoUnit.DAYS);
    List<Long> taskIdList = taskRepository.findAllRemovableBefore(endDate);

    taskRepository.deleteAllById(taskIdList);
    log.info("Tasks deleted: {}", taskIdList.size());
  }
}
