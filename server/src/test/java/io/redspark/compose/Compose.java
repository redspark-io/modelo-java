package io.redspark.compose;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import io.redspark.controller.dto.AgendamentoDTO;
import io.redspark.controller.form.AgendamentoForm;
import io.redspark.domain.City;
import io.redspark.domain.City.CityBuilder;
import io.redspark.domain.Hotel;
import io.redspark.domain.Hotel.HotelBuilder;
import io.redspark.domain.User;
import io.redspark.domain.User.UserBuilder;
import io.redspark.domain.vet.Agendamento;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Dono;
import io.redspark.domain.vet.Vacina;
import io.redspark.domain.vet.Veterinario;

public class Compose {

	private static AtomicLong counter = new AtomicLong();

	public static UserBuilder admin(String name) {
		return user(name).admin(true);
	}

	public static UserBuilder user(String name) {
		return User.builder().name(name).password(name).login(name).admin(false);
	}

	public static CityBuilder city(String name) {
		return City.builder().name(name).country("Country " + counter.incrementAndGet())
				.state("State " + counter.get());
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

	public static Dono.DonoBuilder dono(String nome, String email) {
		return Dono.builder().nome(nome).email(email);
	}

	public static Agendamento.AgendamentoBuilder agendamento(Animal animal, Vacina vacina, Consulta consulta, LocalDateTime data) {
		return Agendamento.builder().animal(animal).consulta(consulta).vacina(vacina).data(data);
	}

	public static AgendamentoForm.AgendamentoFormBuilder agendamentoForm(Animal animal, Vacina vacina, Consulta consulta, LocalDateTime data) {
		return AgendamentoForm.builder()
				.idAnimal(animal.getId())
				.idConsulta(consulta.getId())
				.idVacina(vacina.getId())
				.data(data);
	}

	public static Consulta.ConsultaBuilder consulta(Animal animal, Veterinario veterinario, java.time.LocalDateTime dataCosulta) {
		return Consulta.builder().animal(animal).veterinario(veterinario).data(dataCosulta);
	}

	public static Vacina.VacinaBuilder vacina(String nome) {
		return Vacina.builder().nome(nome);
	}

	public static Veterinario.VeterinarioBuilder veterinario(String nome) {
		return Veterinario.builder().nome(nome);
	}

}
