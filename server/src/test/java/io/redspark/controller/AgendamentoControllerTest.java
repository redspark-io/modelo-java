package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.agendamento;
import static io.redspark.compose.Compose.agendamentoForm;
import static io.redspark.compose.Compose.animal;
import static io.redspark.compose.Compose.consulta;
import static io.redspark.compose.Compose.dono;
import static io.redspark.compose.Compose.vacina;
import static io.redspark.compose.Compose.veterinario;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.redspark.ApplicationTest;
import io.redspark.compose.Compose;
import io.redspark.controller.comparator.AgendamentoDTOComparator;
import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.AgendamentoDTO;
import io.redspark.controller.form.AgendamentoForm;
import io.redspark.domain.User;
import io.redspark.domain.vet.Agendamento;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Dono;
import io.redspark.domain.vet.Vacina;
import io.redspark.domain.vet.Veterinario;
import io.redspark.repository.AgendamentoRepository;
import io.redspark.utils.MapperUtils;

public class AgendamentoControllerTest extends ApplicationTest {

	private MapperUtils<Agendamento, AgendamentoDTO> convert = new MapperUtils<Agendamento, AgendamentoDTO>(Agendamento.class, AgendamentoDTO.class);

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	private Agendamento	agendamentoTotu1;
	private Agendamento	agendamentoTotu2;
	private Agendamento	agendamentoTotu3;

	private Agendamento	agendamentoToti1;
	private Agendamento	agendamentoToti2;
	private Agendamento	agendamentoToti3;

	private Agendamento	agendamentoToto1;
	private Agendamento	agendamentoToto2;
	private Agendamento	agendamentoToto3;

	@Before
	public void before() {

		User admin = admin("Marilda").build();
		saveall(admin);
		signIn(admin);

		Dono dono = save(dono("Octaviano Murakami").build());

		Animal toto = save(animal("Totó", dono).build());
		Animal toti = save(animal("Toti", dono).build());
		Animal totu = save(animal("Totu", dono).build());

		Veterinario veterinario = save(veterinario("Cassio Onishi").build());

		Consulta consultaToto = save(consulta(toto, veterinario, LocalDateTime.now()).build());
		Consulta consultaToti = save(consulta(toti, veterinario, LocalDateTime.now()).build());
		Consulta consultaTotu = save(consulta(totu, veterinario, LocalDateTime.now()).build());

		Vacina tripliceViral = save(vacina("Tríplice Viral").build());
		Vacina hepatiteB = save(vacina("Vacina Hepatite B").build());
		Vacina febreAmarela = save(vacina("Vacina febre amarela").build());

		agendamentoToto1 = save(agendamento(toto, tripliceViral, consultaToto, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		agendamentoToto2 = save(agendamento(toto, hepatiteB, consultaToto, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		agendamentoToto3 = save(agendamento(toto, febreAmarela, consultaToto, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());

		agendamentoToti1 = save(agendamento(toti, tripliceViral, consultaToti, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		agendamentoToti2 = save(agendamento(toti, hepatiteB, consultaToti, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		agendamentoToti3 = save(agendamento(toti, febreAmarela, consultaToti, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());

		agendamentoTotu1 = save(agendamento(totu, tripliceViral, consultaTotu, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		agendamentoTotu2 = save(agendamento(totu, hepatiteB, consultaTotu, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		agendamentoTotu3 = save(agendamento(totu, febreAmarela, consultaTotu, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
	}

	@Test
	public void find_shouldReturnAgendamento() {
		ResponseEntity<AgendamentoDTO[]> responseType = get(ControllerConstants.AGENDAMENTO)
				.expectedStatus(HttpStatus.OK)
				.getResponse(AgendamentoDTO[].class);

		Assert.assertThat(responseType.getBody().length, Matchers.equalTo(9));
	}

	@Test
	public void testPaginacao() {

		Page<AgendamentoDTO> page = get(ControllerConstants.AGENDAMENTO_PAGE)
				.expectedStatus(HttpStatus.OK)
				.getPage(AgendamentoDTO.class);

		assertThat(page.getNumber(), equalTo(0));
		assertThat(page.getSize(), equalTo(50));
		assertThat(page.getTotalElements(), equalTo(9l));
		assertThat(page.getTotalPages(), equalTo(1));
		assertThat(page.getContent(), hasSize(9));

		List<AgendamentoDTO> agendamentos = page.getContent();

//		agendamentots = agendamentos.stream().sorted(AgendamentoDTOComparator.PorData.asc()).collect(Collectors.toList());
		
//		assertThat(agendamentos, contains(
//				convert.toDTO(agendamentoToto1), convert.toDTO(agendamentoToto2), convert.toDTO(agendamentoToto3),
//				convert.toDTO(agendamentoToti1), convert.toDTO(agendamentoToti2), convert.toDTO(agendamentoToti3),
//				convert.toDTO(agendamentoTotu1), convert.toDTO(agendamentoTotu2), convert.toDTO(agendamentoTotu3)));
	}

	@Test
	public void testPaginacaoComParametros() {

		Page<AgendamentoDTO> page = get(ControllerConstants.AGENDAMENTO_PAGE)
				.queryParam("page", "0")
				.queryParam("size", "2")
				.expectedStatus(HttpStatus.OK)
				.getPage(AgendamentoDTO.class);

		assertThat(page.getNumber(), equalTo(0));
		assertThat(page.getSize(), equalTo(2));
		assertThat(page.getTotalElements(), equalTo(9l));
		assertThat(page.getTotalPages(), equalTo(5));
		assertThat(page.getContent(), hasSize(2));

		page = get(ControllerConstants.AGENDAMENTO_PAGE)
				.queryParam("page", "2")
				.queryParam("size", "3")
				.expectedStatus(HttpStatus.OK)
				.getPage(AgendamentoDTO.class);

		assertThat(page.getNumber(), equalTo(2));
		assertThat(page.getSize(), equalTo(3));
		assertThat(page.getTotalElements(), equalTo(9l));
		assertThat(page.getTotalPages(), equalTo(3));
		assertThat(page.getContent(), hasSize(3));
		// assertThat(page.getContent(),
		// contains(convert.toDTO(agendamentoToto),
		// convert.toDTO(agendamentoToti), convert.toDTO(agendamentoTotu)));

	}

	@Test
	public void testPaginacaoComSearchByAnimalNome() {

		Page<AgendamentoDTO> page = get(ControllerConstants.AGENDAMENTO_PAGE)
				.queryParam("search", "Totó")
				.expectedStatus(HttpStatus.OK)
				.getPage(AgendamentoDTO.class);

		assertThat(page.getNumber(), equalTo(0));
		assertThat(page.getSize(), equalTo(50));
		assertThat(page.getTotalElements(), equalTo(3L));
		// assertThat(page.getContent(), contains(convert.toDTO(c1)));

		page = get(ControllerConstants.AGENDAMENTO_PAGE)
				.queryParam("search", "nome inexistente")
				.expectedStatus(HttpStatus.OK)
				.getPage(AgendamentoDTO.class);

		assertThat(page.getNumber(), equalTo(0));
		assertThat(page.getSize(), equalTo(50));
		assertThat(page.getTotalElements(), equalTo(0L));
		// assertThat(page.getContent(), contains(convert.toDTO(c1)));
	}

	@Test
	public void testPaginacaoComSearchByVacinaNome() {

		Dono dono = save(dono("Octaviano Murakami").build());
		Animal animal = save(animal("Totó", dono).build());
		Veterinario veterinario = save(veterinario("Cassio Onishi").build());
		Consulta consulta = save(consulta(animal, veterinario, LocalDateTime.now()).build());
		Vacina vacina = save(vacina("Vacina febre amarela").build());
		Agendamento agendamento = save(agendamento(animal, vacina, consulta, LocalDateTime.now()).build());

		Page<AgendamentoDTO> page = get(ControllerConstants.AGENDAMENTO_PAGE)
				.queryParam("search", "Vacina febre amarela")
				.expectedStatus(HttpStatus.OK)
				.getPage(AgendamentoDTO.class);

		assertThat(page.getNumber(), equalTo(0));
		assertThat(page.getSize(), equalTo(50));
		assertThat(page.getTotalElements(), equalTo(4L));

		// assertThat(page.getContent(), contains(convert.toDTO(agendamento)));
	}

	@Test
	public void testCreate() throws JsonProcessingException, IOException {

		User admin = admin("Naelson").build();
		saveall(admin);
		signIn(admin);

		Dono dono = save(dono("Octaviano Murakami").build());

		Animal animal = save(animal("Totó", dono).build());

		Veterinario veterinario = save(veterinario("Cassio Onishi").build());

		Consulta consulta = save(consulta(animal, veterinario, LocalDateTime.now()).build());

		Vacina tripliceViral = save(vacina("Tríplice Viral").build());

		AgendamentoForm agendamento = (agendamentoForm(animal, tripliceViral, consulta, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());

		ResponseEntity<AgendamentoDTO> response = post(ControllerConstants.AGENDAMENTO)
				.json(agendamento)
				.expectedStatus(HttpStatus.CREATED)
				.getResponse(AgendamentoDTO.class);

		assertThat(response.getBody().getId(), notNullValue());
		assertThat(response.getBody().getIdAnimal(), equalTo(animal.getId()));
		assertThat(response.getBody().getIdConsulta(), equalTo(consulta.getId()));
		assertThat(response.getBody().getIdVacina(), equalTo(tripliceViral.getId()));
		// assertThat(agendamentoRepository.findAll(), hasSize(1));
	}

	@Test
	public void testUpdate() {
		User admin = admin("Naelson").build();
		saveall(admin);
		signIn(admin);

		Dono dono = save(dono("Octaviano Murakami").build());

		Animal animal = save(animal("Totó", dono).build());

		Veterinario veterinario = save(veterinario("Cassio Onishi").build());

		Consulta consulta = save(consulta(animal, veterinario, LocalDateTime.now()).build());

		Vacina vacina = save(vacina("Tríplice Viral").build());

		Agendamento agendamento = save(agendamento(animal, vacina, consulta, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());
		AgendamentoForm newAgendamento = Compose.agendamentoForm(animal, vacina, consulta, LocalDateTime.of(LocalDate.of(1986, 05, 15), LocalTime.of(11, 50))).build();

		ResponseEntity<AgendamentoDTO> response = put(ControllerConstants.AGENDAMENTO + "/%d", agendamento.getId())
				.json(newAgendamento)
				.expectedStatus(HttpStatus.OK)
				.getResponse(AgendamentoDTO.class);

		assertThat(response.getBody().getIdAnimal(), equalTo(animal.getId()));
		assertThat(response.getBody().getIdConsulta(), equalTo(consulta.getId()));
		assertThat(response.getBody().getIdVacina(), equalTo(vacina.getId()));
		assertThat(response.getBody().getData(), equalTo(LocalDateTime.of(LocalDate.of(1986, 05, 15), LocalTime.of(11, 50))));

		Agendamento a = agendamentoRepository.findOne(agendamento.getId());

		// org.hibernate.LazyInitializationException: could not initialize proxy
		// - no Session
		// assertThat(a.getAnimal().getId(),
		// equalTo(newAgendamento.getIdAnimal()));
		// assertThat(a.getConsulta().getId(),
		// equalTo(newAgendamento.getIdConsulta()));
		// assertThat(a.getVacina().getId(),
		// equalTo(newAgendamento.getIdVacina()));
		// assertThat(a.getData(), equalTo(newAgendamento.getData()));
	}

	@Test
	public void testDelete() {
		User admin = admin("Naelson").build();
		saveall(admin);
		signIn(admin);

		Dono dono = save(dono("Octaviano Murakami").build());

		Animal animal = save(animal("Totó", dono).build());

		Veterinario veterinario = save(veterinario("Cassio Onishi").build());

		Consulta consulta = save(consulta(animal, veterinario, LocalDateTime.now()).build());

		Vacina vacina = save(vacina("Tríplice Viral").build());

		Agendamento agendamento = save(agendamento(animal, vacina, consulta, LocalDateTime.of(LocalDate.of(2017, 06, 15), LocalTime.of(14, 25))).build());

		assertThat(agendamentoRepository.findAll(), hasSize(10));

		ResponseEntity<AgendamentoDTO> response = delete(ControllerConstants.AGENDAMENTO + "/%s", agendamento.getId())
				.expectedStatus(HttpStatus.OK)
				.getResponse(AgendamentoDTO.class);

		assertThat(response.getBody().getId(), equalTo(agendamento.getId()));
		assertThat(agendamentoRepository.findAll(), hasSize(9));
	}

	@Test
	public void testReadNotFound() {
		get(ControllerConstants.AGENDAMENTO + "/999")
				.expectedStatus(HttpStatus.NOT_FOUND)
				.getResponse();
	}

	@Test
	public void testDeleteNotFound() {
		delete(ControllerConstants.AGENDAMENTO + "/999")
				.expectedStatus(HttpStatus.NOT_FOUND)
				.getResponse();
	}

	@Test
	public void testUpdateNotFound() {
		Dono dono = save(dono("Octaviano Murakami").build());
		Animal animal = save(animal("Totó", dono).build());
		Veterinario veterinario = save(veterinario("Cassio Onishi").build());
		Consulta consulta = save(consulta(animal, veterinario, LocalDateTime.now()).build());
		Vacina vacina = save(vacina("Tríplice Viral").build());
		Agendamento agendamento = save(agendamento(animal, vacina, consulta, LocalDateTime.now()).build());
		put(ControllerConstants.AGENDAMENTO + "/%s", agendamento.getId() + 1)
				.json(convert.toDTO(agendamento))
				.expectedStatus(HttpStatus.NOT_FOUND)
				.getResponse();
	}

}
