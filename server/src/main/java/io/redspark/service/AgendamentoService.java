package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.redspark.controller.form.AgendamentoForm;
import io.redspark.domain.vet.Agendamento;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Vacina;
import io.redspark.exception.WebException;
import io.redspark.repository.AgendamentoRepository;
import io.redspark.repository.AnimalRepository;
import io.redspark.repository.ConsultaRepository;
import io.redspark.repository.VacinaRepository;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private VacinaRepository vacinaRepository;

	@Transactional(readOnly = true)
	public Agendamento findOne(Long id) {
		return agendamentoRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Agendamento> findAll() {
		return agendamentoRepository.findAll();
	}

	@Transactional
	public Agendamento insert(AgendamentoForm agendamentoForm) {

		if (isNull(agendamentoForm)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Animal animal = animalRepository.findOne(agendamentoForm.getIdAnimal());
		Consulta consulta = consultaRepository.findOne(agendamentoForm.getIdConsulta());
		Vacina vacina = vacinaRepository.findOne(agendamentoForm.getIdVacina());

		if (isNull(agendamentoForm.getData()) || isNull(animal) || isNull(vacina) || isNull(consulta)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Agendamento agendamento = Agendamento.builder()
				.consulta(consulta)
				.vacina(vacina)
				.animal(animal)
				.data(agendamentoForm.getData())
				.build();

		return agendamentoRepository.save(agendamento);
	}

	@Transactional
	public Agendamento update(Long id, AgendamentoForm agendamentoForm) {

		if (isNull(id) || isNull(agendamentoForm) || isNull(agendamentoForm.getData())) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Agendamento agendamento = agendamentoRepository.findOne(id);
		Animal animal = animalRepository.findOne(agendamentoForm.getIdAnimal());
		Consulta consulta = consultaRepository.findOne(agendamentoForm.getIdConsulta());
		Vacina vacina = vacinaRepository.findOne(agendamentoForm.getIdVacina());

		if (isNull(consulta) || isNull(animal) || isNull(vacina) || isNull(agendamento)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		agendamento.setAnimal(animal);
		agendamento.setConsulta(consulta);
		agendamento.setVacina(vacina);
		agendamento.setData(agendamentoForm.getData());

		agendamentoRepository.save(agendamento);

		return agendamento;
	}

	@Transactional
	public HttpStatus delete(Long id) {

		if (isNull(id)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		HttpStatus retorno;
		Agendamento agendamento = agendamentoRepository.findOne(id);

		if (isNull(agendamento)) {
			retorno = HttpStatus.NOT_FOUND;
		} else {
			agendamentoRepository.delete(agendamento);
			retorno = HttpStatus.OK;
		}

		return retorno;
	}

}
