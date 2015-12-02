package io.redspark.utils;

import org.springframework.util.Assert;

public class SQLLikeUtils {

  public static String like(String value) {
    Assert.hasText(value, "Value can't be null");
    return endsWith(startWith(value));
  }

  public static String startWith(String value) {
    return value + "%";
  }

  public static String endsWith(String value) {
    Assert.hasText(value, "Value can't be null");
    return "%" + value;
  }
}
