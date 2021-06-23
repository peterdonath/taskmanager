package com.example.taskmanager.domain.task;

import com.example.taskmanager.domain.shared.AbstractEntity;
import com.example.taskmanager.domain.shared.Status;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author donath.peter@gmail.com
 */

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TMS_TASK", indexes = {
    @Index(name = "TMS_TASK_U1", columnList = "TASK_ID", unique = true)
})
public class Task extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TASK_ID", nullable = false)
  private Long id;

  @Setter
  @Column(name = "USER_ID", nullable = false)
  private Long userId;

  @Setter
  @Column(name = "TASK_NAME", length = 100, nullable = false)
  private String name;

  @Setter
  @Column(name = "DESCRIPTION", length = 4000)
  private String description;

  @Setter
  @Column(name = "DATE_TIME", columnDefinition = "TIMESTAMP")
  private LocalDateTime dateTime;

  @Setter
  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private Status status;
}
