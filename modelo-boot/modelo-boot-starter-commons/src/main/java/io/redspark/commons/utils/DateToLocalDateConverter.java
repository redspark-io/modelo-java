package io.redspark.commons.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

public class DateToLocalDateConverter extends CustomConverter<Date, LocalDate> {

  @Override
  public LocalDate convert(Date source, Type<? extends LocalDate> destinationType) {
    return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}