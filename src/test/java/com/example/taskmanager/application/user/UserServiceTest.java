package com.example.taskmanager.application.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.taskmanager.application.user.dto.UserDto;
import com.example.taskmanager.application.user.service.UserServiceImpl;
import com.example.taskmanager.domain.user.User;
import com.example.taskmanager.domain.user.UserRepository;
import com.example.taskmanager.infrastructure.exception.UserExistedException;
import java.util.Arrays;
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
import org.springframework.data.domain.Pageable;

/**
 * @author donath.peter@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void userListTest() {
    Pageable pageable = PageRequest.of(0, 20);
    List<User> userList = Arrays.asList(this.generateUser(1L), this.generateUser(2L));
    Page<User> userPage = new PageImpl<>(userList, pageable, 2);

    when(userRepository.findAllByActiveFlag(anyBoolean(), any())).thenReturn(userPage);

    Page<UserDto> result = userService.getAllUsers(pageable);

    assertEquals(userList.size(), result.getTotalElements());
  }

  @Test
  public void userInfoNotFoundTest() {
    when(userRepository.findById(any())).thenReturn(Optional.empty());

    Optional<UserDto> result = userService.getUserInfo(1L);

    assertTrue(result.isEmpty());
  }

  @Test
  public void userInfoFoundTest() {
    User user = this.generateUser(1L);
    when(userRepository.findById(any())).thenReturn(Optional.of(user));

    Optional<UserDto> result = userService.getUserInfo(1L);

    assertTrue(result.isPresent());
    assertEquals(user.getUserName(), result.get().getUserName());
    assertEquals(user.getFirstName(), result.get().getFirstName());
    assertEquals(user.getLastName(), result.get().getLastName());
  }

  @Test(expected = UserExistedException.class)
  public void addUserFailTest() throws UserExistedException, IllegalAccessException {
    User user = this.generateUser(1L);
    FieldUtils.writeField(user, "activeFlag", true, true);

    when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));

    userService.addUser(this.generateUserDto(1L));
  }

  @Test
  public void addUserSuccessTest() throws IllegalAccessException, UserExistedException {
    UserDto userDto = this.generateUserDto(1L);
    FieldUtils.writeField(userDto, "id", null, true);

    when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class)))
        .then(inp -> inp.getArgument(0));

    UserDto result = userService.addUser(userDto);

    assertEquals(userDto.getUserName(), result.getUserName());
    assertEquals(userDto.getFirstName(), result.getFirstName());
    assertEquals(userDto.getLastName(), result.getLastName());
  }

  @Test
  public void updateUserNotFoundTest() {
    UserDto updateUserDto = this.generateUserDto(1L);

    when(userRepository.findById(any())).thenReturn(Optional.empty());

    Optional<UserDto> result = userService.updateUser(1L, updateUserDto);

    assertTrue(result.isEmpty());
  }

  @Test
  public void updateUserFoundTest() {
    UserDto updateUserDto = this.generateUserDto(2L);
    User existingUser = this.generateUser(1L);

    when(userRepository.findById(any())).thenReturn(Optional.of(existingUser));

    Optional<UserDto> result = userService.updateUser(1L, updateUserDto);

    assertTrue(result.isPresent());
    assertEquals(existingUser.getUserName(), result.get().getUserName());
    assertEquals(updateUserDto.getFirstName(), result.get().getFirstName());
    assertEquals(updateUserDto.getLastName(), result.get().getLastName());
  }

  private User generateUser(Long id) {
    return User.builder()
        .id(id)
        .userName("userName".concat(id.toString()))
        .firstName("firstName".concat(id.toString()))
        .lastName("lastName".concat(id.toString()))
        .build();
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
