package io.redspark.compose;

import java.util.concurrent.atomic.AtomicLong;

import io.redspark.domain.User;
import io.redspark.domain.User.UserBuilder;

public class Compose {

  private static AtomicLong counter = new AtomicLong();

  public static UserBuilder admin(String name) {
    return user(name).admin(true);
  }

  public static UserBuilder user(String name) {
    return User.builder().name(name).password(name).login(name).admin(false);
  }
}
