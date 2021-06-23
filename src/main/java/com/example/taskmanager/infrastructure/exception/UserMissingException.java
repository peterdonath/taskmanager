package com.example.taskmanager.infrastructure.exception;

/**
 * @author donath.peter@gmail.com
 */

public class UserMissingException extends Exception {

  /**
   * Constructor for Exception
   *
   * @param errorMessage errorMessage
   */
  public UserMissingException(String errorMessage) {
    super(errorMessage);
  }
}
