package com.example.taskmanager.domain.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author donath.peter@gmail.com
 */

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  /**
   * Retrieves all records from the database where activeFlag matches
   *
   * @param activeFlag activeFlag
   * @param pageable   {@link Pageable}
   * @return Page of {@link User} entities
   */
  Page<User> findAllByActiveFlag(boolean activeFlag, Pageable pageable);

  /**
   * Retrieves a user by userName
   *
   * @param userName userName
   * @return Optional of {@link User}
   */
  Optional<User> findByUserName(String userName);

}
