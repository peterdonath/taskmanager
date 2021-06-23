package com.example.taskmanager.domain.task;

import com.example.taskmanager.domain.shared.Status;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author donath.peter@gmail.com
 */

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

  /**
   * Retrieves all user related tasks with specific activeFlag
   *
   * @param userId     userId
   * @param activeFlag activeFlag
   * @param pageable   Pageable
   * @return Page of {@link Task}
   */
  Page<Task> findAllByUserIdAndActiveFlag(Long userId, boolean activeFlag, Pageable pageable);

  /**
   * Retrieves a User Task by Id
   *
   * @param userId     userId
   * @param taskId     taskId
   * @param activeFlag activeFlag
   * @return Optional of {@link Task}
   */
  Optional<Task> findByUserIdAndIdAndActiveFlag(Long userId, Long taskId, boolean activeFlag);

  /**
   * Retrieves all disabled Tasks ids before the given date
   * @param endDate endDate
   * @return List of ids
   */
  @Query("SELECT t.id FROM Task t WHERE t.activeFlag = false AND t.lastUpdateDate < :endDate")
  List<Long> findAllRemovableBefore(LocalDateTime endDate);

  /**
   * Retrieves all Tasks ids before the given date and given status
   * @param endDate endDate
   * @param status status
   * @return List of ids
   */
  @Query("SELECT t.id FROM Task t WHERE t.activeFlag = true AND t.dateTime < :endDate AND t.status = :status")
  List<Long> findAllTasksBefore(LocalDateTime endDate, Status status);
}
