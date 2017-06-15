package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.animal;
import static io.redspark.compose.Compose.consulta;
import static io.redspark.compose.Compose.dono;
import static io.redspark.compose.Compose.veterinario;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.ConsultaDTO;
import io.redspark.domain.User;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Dono;
import io.redspark.domain.vet.Veterinario;

public class ConsultaControllerTest extends ApplicationTest {

	@Before
	public void before() {

		User admin = admin("Naelson").build();
		saveall(admin);
		signIn(admin);

		Dono dono = save(dono("Naelson Vet").build());

		Animal toto = save(animal("Totó", dono).build());
		Animal toti = save(animal("Toti", dono).build());
		Animal totu = save(animal("Totu", dono).build());

		Veterinario veterinario = save(veterinario("João da Silva").build());

		save(consulta(toto, veterinario, LocalDateTime.now()).build());
		save(consulta(toti, veterinario, LocalDateTime.now()).build());
		save(consulta(totu, veterinario, LocalDateTime.now()).build());
	}

	@Test
	public void find_shouldReturnConsultas() {
		ResponseEntity<ConsultaDTO[]> responseType = get(ControllerConstants.CONSULTA)
				.expectedStatus(HttpStatus.OK)
				.getResponse(ConsultaDTO[].class);
		Assert.assertThat(responseType.getBody().length, Matchers.equalTo(3));
	}
}
