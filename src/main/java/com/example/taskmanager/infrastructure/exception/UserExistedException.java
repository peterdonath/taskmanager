package com.example.taskmanager.infrastructure.exception;

/**
 * @author donath.peter@gmail.com
 */

public class UserExistedException extends Exception {

  /**
   * Constructor for Exception
   *
   * @param message message
   */
  public UserExistedException(String message) {
    super(message);
  }
}
