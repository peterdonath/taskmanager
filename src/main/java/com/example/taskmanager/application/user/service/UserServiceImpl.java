package com.example.taskmanager.application.user.service;

import com.example.taskmanager.application.user.dto.UserDto;
import com.example.taskmanager.domain.user.User;
import com.example.taskmanager.domain.user.UserRepository;
import com.example.taskmanager.infrastructure.exception.UserExistedException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author donath.peter@gmail.com
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Page<UserDto> getAllUsers(Pageable pageable) {
    return userRepository.findAllByActiveFlag(true, pageable)
        .map(UserDto::mapEntityToDto);
  }

  @Override
  public Optional<UserDto> getUserInfo(Long userId) {
    return userRepository.findById(userId).map(UserDto::mapEntityToDto);
  }

  @Override
  public UserDto addUser(UserDto userDto) throws UserExistedException {
    Optional<User> userOptional = userRepository.findByUserName(userDto.getUserName());

    if (userOptional.isPresent() && userOptional.get().isActiveFlag()) {
      throw new UserExistedException("User Already Exists: ".concat(userDto.getUserName()));
    }

    User newUser = UserDto.mapDtoToEntity(userDto);
    userRepository.save(newUser);

    return UserDto.mapEntityToDto(newUser);
  }

  @Override
  @Transactional
  public Optional<UserDto> updateUser(Long userId, UserDto userDto) {
    Optional<User> userOptional = userRepository.findById(userId);

    if (userOptional.isEmpty()) {
      return Optional.empty();
    }

    User user = userOptional.get();
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());

    return Optional.of(UserDto.mapEntityToDto(user));
  }
}
