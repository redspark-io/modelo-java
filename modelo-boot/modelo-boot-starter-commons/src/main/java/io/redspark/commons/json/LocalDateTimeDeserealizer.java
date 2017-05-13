package io.redspark.commons.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserealizer extends JsonDeserializer<LocalDateTime> {
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

  @Override
  public LocalDateTime deserialize(JsonParser jp, DeserializationContext arg1)
      throws IOException, JsonProcessingException {
    return LocalDateTime.parse(jp.getText(), DATE_FORMAT);
  }

}
