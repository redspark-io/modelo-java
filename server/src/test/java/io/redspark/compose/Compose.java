package io.redspark.compose;

import java.util.concurrent.atomic.AtomicLong;

import io.redspark.domain.City;
import io.redspark.domain.City.CityBuilder;
import io.redspark.domain.Hotel;
import io.redspark.domain.Hotel.HotelBuilder;
import io.redspark.domain.User;
import io.redspark.domain.User.UserBuilder;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Dono;

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

  public static Animal.AnimalBuilder animal(String nome, Dono dono) {
    return Animal.builder().nome(nome).dono(dono);
  }
  
  public static Dono.DonoBuilder dono(String nome) {
    return Dono.builder().nome(nome);
  }
}
