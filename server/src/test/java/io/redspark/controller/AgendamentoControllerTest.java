package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.agendamento;
import static io.redspark.compose.Compose.animal;
import static io.redspark.compose.Compose.consulta;
import static io.redspark.compose.Compose.dono;
import static io.redspark.compose.Compose.vacina;
import static io.redspark.compose.Compose.veterinario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.redspark.ApplicationTest;
import io.redspark.compose.Compose;
import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.AgendamentoDTO;
import io.redspark.domain.User;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Dono;
import io.redspark.domain.vet.Vacina;
import io.redspark.domain.vet.Veterinario;

public class AgendamentoControllerTest extends ApplicationTest {

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

		Consulta consultaToto = save(consulta(toto, veterinario, LocalDateTime.now()).build());
		Consulta consultaToti = save(consulta(toti, veterinario, LocalDateTime.now()).build());
		Consulta consultaTotu = save(consulta(totu, veterinario, LocalDateTime.now()).build());

		Vacina tripliceViral = save(vacina("Tríplice Viral").build());
		Vacina hepatiteB = save(vacina("Vacina Hepatite B").build());
		Vacina febreAmarela = save(vacina("Vacina febre amarela").build());

		save(agendamento(toto, tripliceViral, consultaToto, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));
		save(agendamento(toto, hepatiteB, consultaToto, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));
		save(agendamento(toto, febreAmarela, consultaToto, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));

		save(agendamento(toti, tripliceViral, consultaToti, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));
		save(agendamento(toti, hepatiteB, consultaToti, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));
		save(agendamento(toti, febreAmarela, consultaToti, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));

		save(agendamento(totu, tripliceViral, consultaTotu, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));
		save(agendamento(totu, hepatiteB, consultaTotu, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));
		save(agendamento(totu, febreAmarela, consultaTotu, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))));

	}

	@Test
	public void find_shouldReturnAgendamento() {
		ResponseEntity<AgendamentoDTO[]> responseType = get(ControllerConstants.AGENDAMENTO)
				.expectedStatus(HttpStatus.OK)
				.getResponse(AgendamentoDTO[].class);
		Assert.assertThat(responseType.getBody().length, Matchers.equalTo(9));
	}
}
