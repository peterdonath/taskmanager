package com.example.taskmanager.infrastructure.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author donath.peter@gmail.com
 */

@Slf4j
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    try {
      String s = value.format(DATE_FORMATTER);
      gen.writeString(s);
    } catch (DateTimeParseException e) {
      log.error(e.getMessage(), e);
      gen.writeString("");
    }
  }
}
