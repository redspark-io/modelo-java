package io.redspark.compose;

import java.util.concurrent.atomic.AtomicLong;

import io.redspark.domain.City;
import io.redspark.domain.Hotel;
import io.redspark.domain.User;
import io.redspark.domain.City.CityBuilder;
import io.redspark.domain.Hotel.HotelBuilder;
import io.redspark.domain.User.UserBuilder;

public class Compose {

  private static AtomicLong counter = new AtomicLong();

  public static UserBuilder admin(String name) {
    return user(name).admin(true);
  }

  public static UserBuilder user(String name) {
    return User.builder().name(name).password(name).login(name).admin(false);
  }

  public static CityBuilder city(String name) {
    return City.builder().name(name).country("Country " + counter.incrementAndGet()).state("State " + counter.get());
  }

  public static HotelBuilder hotel(String name, City city) {
    return Hotel.builder().name(name).address("Rua Quintana, 753").city(city).zip("12345-123");
  }
}
