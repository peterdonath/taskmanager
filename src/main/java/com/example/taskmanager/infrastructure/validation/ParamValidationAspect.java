package com.example.taskmanager.infrastructure.validation;

import com.example.taskmanager.domain.user.User;
import com.example.taskmanager.domain.user.UserRepository;
import com.example.taskmanager.infrastructure.exception.UserMissingException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This AOP code should validate @ValidateParam annotated methods.
 * It checks if non the attributes are null and takes the firs attribute to check if a User exists with the id
 *
 * @author donath.peter@gmail.com
 */

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParamValidationAspect {

  private final UserRepository userRepository;

  /**
   * Checks if the user existed
   * @param joinPoint joinPoint
   * @throws UserMissingException Exception
   */
  @Before(value = "@annotation(com.example.taskmanager.infrastructure.annotation.ValidateParams)")
  public void validateParamsAndUser(JoinPoint joinPoint) throws UserMissingException {
    if (Arrays.stream(joinPoint.getArgs()).anyMatch(Objects::isNull)) {
      throw new NullPointerException("Parameter is null");
    }

    try {
      Long userId = (Long) joinPoint.getArgs()[0];

      Optional<User> user = userRepository.findById(userId);
      if (user.isEmpty() || !user.get().isActiveFlag()) {
        throw new UserMissingException("User with userId: ".concat(userId.toString()).concat(" is missing"));
      }
    } catch (ClassCastException e) {
      log.error(e.getMessage(), e);
    }
  }

}
