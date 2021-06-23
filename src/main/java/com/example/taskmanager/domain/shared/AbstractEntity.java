package com.example.taskmanager.domain.shared;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author donath.peter@gmail.com
 */

@Getter
@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

  @Column(name = "ACTIVE_FLAG")
  private boolean activeFlag = true;

  @CreatedDate
  @Column(name = "CREATION_DATE", columnDefinition = "TIMESTAMP")
  private LocalDateTime creationDate;

  @LastModifiedDate
  @Column(name = "LAST_UPDATE_DATE", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastUpdateDate;

  @Version
  @Column(name = "VERSION")
  private long version;

  /**
   * Sets the active flag to false
   */
  public void disable() {
    this.activeFlag = false;
  }
}
