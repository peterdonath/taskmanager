package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author donath.peter@gmail.com
 */
@EnableScheduling
@ComponentScan
@SpringBootApplication
public class TaskmanagerApplication {

  /**
   * Application run class
   * @param args String[] args
   */
  public static void main(String[] args) {
    SpringApplication.run(TaskmanagerApplication.class, args);
  }

}
