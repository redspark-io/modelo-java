package io.redspark.commons.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.metadata.Type;

public class LocalDateToDateConverter extends CustomConverter<LocalDate, Date> {

  @Override
  public Date convert(LocalDate source, Type<? extends Date> destinationType) {
    return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}