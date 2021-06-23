package com.example.taskmanager.domain.user;

import com.example.taskmanager.domain.shared.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TMS_USER", indexes = {
      @Index(name = "TMS_USER_U1", columnList = "USER_ID", unique = true),
      @Index(name = "TMS_USER_U2", columnList = "USER_NAME", unique = true)
    })
public class User extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "USER_ID", nullable = false)
  private Long id;

  @Column(name = "USER_NAME", length = 100, nullable = false)
  private String userName;

  @Setter
  @Column(name = "FIRST_NAME", length = 100)
  private String firstName;

  @Setter
  @Column(name = "LAST_NAME", length = 100)
  private String lastName;
}
