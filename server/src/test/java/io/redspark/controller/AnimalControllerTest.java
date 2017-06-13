package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.animal;
import static io.redspark.compose.Compose.dono;
import static org.springframework.http.HttpStatus.OK;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.domain.User;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Dono;

public class AnimalControllerTest extends ApplicationTest {

  @Before
  public void before() {

    User amind = admin("Naelson").build();
    saveall(amind);
    signIn(amind);

    Dono naelson = save(dono("Naelson Vet").build());

    save(animal("Totó", naelson).build());
    save(animal("Toti", naelson).build());
    save(animal("Totu", naelson).build());
  }

  @Test
  public void find_shouldReturnAnimals() {

    ResponseEntity<Animal[]> response = get(ControllerConstants.ANIMAL)
        .expectedStatus(OK)
        .getResponse(Animal[].class);

    Assert.assertThat(response.getBody().length, Matchers.equalTo(3));
  }
}
