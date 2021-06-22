package com.example.taskmanager.domain.user;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author peter.donath@gmail.com
 */

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
