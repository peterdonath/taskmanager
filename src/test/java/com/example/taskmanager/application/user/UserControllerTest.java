package com.example.taskmanager.application.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.taskmanager.application.user.controller.UserControllerImpl;
import com.example.taskmanager.application.user.dto.UserDto;
import com.example.taskmanager.application.user.service.UserService;
import com.example.taskmanager.infrastructure.exception.UserExistedException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author donath.peter@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserControllerImpl userController;

  @Test
  public void emptyUserPageTest() {
    Page<UserDto> emptyPage = new PageImpl<UserDto>(Collections.emptyList(), PageRequest.of(0, 20), 0);

    when(userService.getAllUsers(any())).thenReturn(emptyPage);

    ResponseEntity<Page<UserDto>> result = userController.getAllUsers(null);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.getBody().isEmpty());
  }

  @Test
  public void someUserListTest() {
    List userList = Arrays.asList(this.generateUserDto(1L), this.generateUserDto(2L));
    Page<UserDto> usersPage = new PageImpl<UserDto>(userList, PageRequest.of(0, 20), 2);

    when(userService.getAllUsers(any())).thenReturn(usersPage);

    ResponseEntity<Page<UserDto>> result = userController.getAllUsers(null);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertFalse(result.getBody().isEmpty());
    assertEquals(2, result.getBody().getTotalElements());
  }

  @Test
  public void getUserNotFoundTest() {
    when(userService.getUserInfo(any())).thenReturn(Optional.empty());

    ResponseEntity<UserDto> result = userController.getUserInfo(1L);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  @Test
  public void userInfoTest() {
    when(userService.getUserInfo(any())).thenReturn(Optional.of(this.generateUserDto(1L)));

    ResponseEntity<UserDto> result = userController.getUserInfo(1L);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertTrue(result.hasBody());
  }

  @Test
  public void userAddDuplicateTest() throws UserExistedException {
    doThrow(new UserExistedException("User Exists")).when(userService).addUser(any());

    ResponseEntity<UserDto> result = userController.addUser(this.generateUserDto(0L));

    assertEquals(HttpStatus.NOT_MODIFIED, result.getStatusCode());
    assertFalse(result.hasBody());
  }

  @Test
  public void userAddIncompleteTest() throws UserExistedException, IllegalAccessException {
    UserDto userDto = this.generateUserDto(0L);
    FieldUtils.writeField(userDto, "userName", null, true);

    ResponseEntity<UserDto> result = userController.addUser(userDto);

    assertEquals(HttpStatus.PARTIAL_CONTENT, result.getStatusCode());
    assertFalse(result.hasBody());
  }

  @Test
  public void userAddSuccessfulTest() throws UserExistedException {
    UserDto userDto = this.generateUserDto(1L);

    when(userService.addUser(any())).thenReturn(userDto);

    ResponseEntity<UserDto> result = userController.addUser(userDto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertTrue(result.hasBody());
  }

  @Test
  public void updateUserNotFoundTest() {
    UserDto userDto = this.generateUserDto(1L);

    when(userService.updateUser(any(), any())).thenReturn(Optional.empty());

    ResponseEntity<UserDto> result = userController.updateUser(1L, userDto);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    assertFalse(result.hasBody());
  }

  @Test
  public void updateUserSuccessfulTest() {
    UserDto userDto = this.generateUserDto(1L);

    when(userService.updateUser(any(), any())).thenReturn(Optional.of(userDto));

    ResponseEntity<UserDto> result = userController.updateUser(1L, userDto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertTrue(result.hasBody());
  }

  private UserDto generateUserDto(Long id) {
    return UserDto.builder()
        .id(id)
        .userName("username".concat(id.toString()))
        .firstName("FirstName".concat(id.toString()))
        .lastName("LastName".concat(id.toString()))
        .build();
  }
}
