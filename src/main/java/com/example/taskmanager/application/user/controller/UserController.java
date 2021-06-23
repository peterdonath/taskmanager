package com.example.taskmanager.application.user.controller;

import com.example.taskmanager.application.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * @author donath.peter@gmail.com
 */

public interface UserController {

  /**
   * Retrieves all {@link UserDto} one Page at a time
   *
   * @param pageable pageable
   * @return Page of UserDto
   */
  ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable);

  /**
   * Retrieves one {@link UserDto} by Id
   *
   * @param id id
   * @return {@link UserDto}
   */
  ResponseEntity<UserDto> getUserInfo(Long id);

  /**
   * Persists one {@link UserDto} in the application
   *
   * @param userDto userDto
   * @return Persisted {@link UserDto}
   */
  ResponseEntity<UserDto> addUser(UserDto userDto);

  /**
   * Updates a {@link UserDto} in the application
   *
   * @param id      userId
   * @param userDto userDto
   * @return Updated {@link UserDto}
   */
  ResponseEntity<UserDto> updateUser(Long id, UserDto userDto);
}
