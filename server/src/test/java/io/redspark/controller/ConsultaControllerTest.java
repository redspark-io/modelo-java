package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.animal;
import static io.redspark.compose.Compose.consulta;
import static io.redspark.compose.Compose.dono;
import static io.redspark.compose.Compose.veterinario;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.domain.User;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Dono;
import io.redspark.domain.vet.Veterinario;

public class ConsultaControllerTest extends ApplicationTest {

	@Before
	public void before() {

		User admin = admin("Naelson").build();
		saveall(admin);
		signIn(admin);

		Dono naelson = save(dono("Naelson Vet").build());

		Animal toto = save(animal("Totó", naelson).build());
		Animal toti = save(animal("Toti", naelson).build());
		Animal totu = save(animal("Totu", naelson).build());

		Veterinario veterinario = save(veterinario("João da Silva").build());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");

		try {

			save(consulta(toto, veterinario, sdf.parse("13-05-2017")).build());
			save(consulta(toti, veterinario, sdf.parse("13-05-2017")).build());
			save(consulta(totu, veterinario, sdf.parse("13-05-2017")).build());
			save(consulta(toto, veterinario, sdf.parse("13-06-2017")).build());
			save(consulta(toti, veterinario, sdf.parse("13-06-2017")).build());
			save(consulta(totu, veterinario, sdf.parse("13-06-2017")).build());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void find_shouldReturnConsultas() {
		ResponseEntity<Consulta[]> responseType = get(ControllerConstants.CONSULTA).expectedStatus(HttpStatus.OK).getResponse(Consulta[].class);
		Assert.assertThat(responseType.getBody().length, Matchers.equalTo(6));
	}

}
