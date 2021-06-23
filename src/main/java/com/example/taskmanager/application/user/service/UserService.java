package com.example.taskmanager.application.user.service;

import com.example.taskmanager.application.user.dto.UserDto;
import com.example.taskmanager.infrastructure.exception.UserExistedException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author donath.peter@gmail.com
 */

public interface UserService {

  /**
   * Retrieves all users from the database
   *
   * @param pageable {@link Pageable}
   * @return {@link Page} of UserDtos
   */
  Page<UserDto> getAllUsers(Pageable pageable);

  /**
   * Retrieves User details
   *
   * @param userId userId
   * @return Optional of {@link UserDto}
   */
  Optional<UserDto> getUserInfo(Long userId);

  /**
   * Persists a new User into the Database
   *
   * @param userDto {@link UserDto}
   * @return The saved {@link UserDto}
   * @throws UserExistedException if a User with the same username was already existed
   */
  UserDto addUser(UserDto userDto) throws UserExistedException;

  /**
   * Updates an existing user
   *
   * @param userId  userId
   * @param userDto {@link UserDto}
   * @return The updated {@link UserDto}
   */
  Optional<UserDto> updateUser(Long userId, UserDto userDto);
}
