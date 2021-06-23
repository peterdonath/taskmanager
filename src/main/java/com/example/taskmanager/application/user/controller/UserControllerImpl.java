package com.example.taskmanager.application.user.controller;

import com.example.taskmanager.application.user.dto.UserDto;
import com.example.taskmanager.application.user.service.UserService;
import com.example.taskmanager.infrastructure.exception.UserExistedException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author donath.peter@gmail.com
 */

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  @GetMapping("/api/user")
  public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.getAllUsers(pageable));
  }

  @Override
  @GetMapping("/api/user/{id}")
  public ResponseEntity<UserDto> getUserInfo(@PathVariable Long id) {
    return ResponseEntity.of(userService.getUserInfo(id));
  }

  @Override
  @PostMapping("/api/user")
  public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
    if (userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
      return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
    try {
      return ResponseEntity.of(Optional.of(userService.addUser(userDto)));
    } catch (UserExistedException e) {
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
  }

  @Override
  @PutMapping("/api/user/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
    return ResponseEntity.of(userService.updateUser(id, userDto));
  }
}
