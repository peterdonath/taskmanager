package com.example.taskmanager.application.user.dto;

import com.example.taskmanager.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author donath.peter@gmail.com
 */

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

  private Long id;

  @JsonProperty(value = "username")
  private String userName;

  @JsonProperty(value = "first_name")
  private String firstName;

  @JsonProperty(value = "last_name")
  private String lastName;

  /**
   * Maps a {@link User} Entity to a {@link UserDto}
   *
   * @param user {@link User} Entity
   * @return {@link UserDto}
   */
  public static UserDto mapEntityToDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .userName(user.getUserName())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .build();
  }

  /**
   * Maps a {@link UserDto} to a {@link User} Entity
   *
   * @param userDto {@link UserDto}
   * @return {@link User}
   */
  public static User mapDtoToEntity(UserDto userDto) {
    return User.builder()
        .userName(userDto.getUserName())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .build();
  }
}
