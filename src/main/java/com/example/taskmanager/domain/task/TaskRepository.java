package com.example.taskmanager.domain.task;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author peter.donath@gmail.com
 */

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

}
