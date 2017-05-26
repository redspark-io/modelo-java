package io.redspark.compose;

import io.redspark.domain.User;
import io.redspark.domain.User.UserBuilder;

public class Compose {
  public static UserBuilder admin(String name) {
    return user(name).admin(true).id(1L);
  }

  public static UserBuilder user(String name) {
    return User.builder().name(name).password(name).login(name).admin(false);
  }
}
