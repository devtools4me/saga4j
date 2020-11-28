package me.devtools4.saga4j.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;

public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
  public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
    return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
  }

  public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
  }
}