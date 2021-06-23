package com.example.taskmanager.application.task.dto;

import com.example.taskmanager.domain.shared.Status;
import com.example.taskmanager.domain.task.Task;
import com.example.taskmanager.infrastructure.json.LocalDateTimeDeserializer;
import com.example.taskmanager.infrastructure.json.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
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
public class TaskDto {

  private Long id;

  private String name;

  private String description;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonProperty(value = "date_time")
  private LocalDateTime dateTime;

  private Status status;

  /**
   * Maps a {@link Task} Entity to {@link TaskDto}
   *
   * @param task {@link Task}
   * @return {@link TaskDto}
   */
  public static TaskDto mapEntityToDto(Task task) {
    return TaskDto.builder()
        .id(task.getId())
        .name(task.getName())
        .description(task.getDescription())
        .dateTime(task.getDateTime())
        .status(task.getStatus())
        .build();
  }

  /**
   * Maps a {@link TaskDto} to a {@link Task} Entity
   *
   * @param taskDto {@link TaskDto}
   * @return {@link Task}
   */
  public static Task mapDtoToEntity(TaskDto taskDto) {
    return Task.builder()
        .name(taskDto.getName())
        .description(taskDto.getDescription())
        .dateTime(taskDto.getDateTime())
        .status(taskDto.getStatus())
        .build();
  }
}
