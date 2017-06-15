package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.vacina;
import static org.springframework.http.HttpStatus.OK;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.controller.constant.ControllerConstants;
import io.redspark.domain.User;
import io.redspark.domain.vet.Vacina;

public class VacinaControllerTest extends ApplicationTest {

	@Before
	public void before() {

		User amind = admin("Naelson").build();
		saveall(amind);
		signIn(amind);

		save(vacina("Tríplice Viral").build());
		save(vacina("Vacina Hepatite B").build());
		save(vacina("Vacina febre amarela").build());
	}

	@Test
	public void find_shouldReturnVacinas() {

		ResponseEntity<Vacina[]> response = get(ControllerConstants.VACINA)
				.expectedStatus(OK)
				.getResponse(Vacina[].class);

		Assert.assertThat(response.getBody().length, Matchers.equalTo(3));
	}
}
