package io.redspark.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends WebException {

  private static final long serialVersionUID = 2799179465862669781L;

  public NotFoundException(Class<?> clazz) {
    super(HttpStatus.NOT_FOUND, clazz.getName() + ".notFound");
  }

}
