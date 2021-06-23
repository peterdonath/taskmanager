package com.example.taskmanager.domain.task;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

}
