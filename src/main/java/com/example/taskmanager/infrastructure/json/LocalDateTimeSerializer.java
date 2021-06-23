package com.example.taskmanager.infrastructure.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author donath.peter@gmail.com
 */

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    try {
      String s = value.format(DATE_FORMATTER);
      gen.writeString(s);
    } catch (DateTimeParseException e) {
      System.err.println(e);
      gen.writeString("");
    }
  }
}
