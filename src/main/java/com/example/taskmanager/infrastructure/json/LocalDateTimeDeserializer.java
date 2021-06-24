package com.example.taskmanager.infrastructure.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author donath.peter@gmail.com
 */

@Slf4j
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx)
      throws IOException {
    String str = p.getText();
    try {
      return LocalDateTime.parse(str, LocalDateTimeSerializer.DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }
}
