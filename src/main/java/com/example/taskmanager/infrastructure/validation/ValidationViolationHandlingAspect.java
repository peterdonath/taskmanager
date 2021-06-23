package com.example.taskmanager.infrastructure.validation;

import java.lang.reflect.UndeclaredThrowableException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author donath.peter@gmail.com
 */

@Aspect
@Component
public class ValidationViolationHandlingAspect {

  /**
   * Captured methods definition
   */
  @Pointcut("execution(public * com.example.taskmanager.application.task.controller.TaskController+.*(..))")
  public void monitor() {

  }

  /**
   * This Advise is used to handle upcoming Exceptions raised during validation
   *
   * @param proceedingJoinPoint proceedingJoinPoint
   * @return The original Object or Bad Request
   */
  @Around("monitor()")
  public Object handleValidationViolation(ProceedingJoinPoint proceedingJoinPoint) {
    try {
      return proceedingJoinPoint.proceed();
    } catch (Throwable exception) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(((UndeclaredThrowableException) exception).getUndeclaredThrowable().getMessage());
    }
  }

}
