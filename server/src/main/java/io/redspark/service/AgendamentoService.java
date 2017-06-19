package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import io.redspark.controller.dto.AgendamentoDTO;
import io.redspark.controller.form.AgendamentoForm;
import io.redspark.domain.City;
import io.redspark.domain.vet.Agendamento;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Vacina;
import io.redspark.exception.NotFoundException;
import io.redspark.exception.WebException;
import io.redspark.repository.AgendamentoRepository;
import io.redspark.repository.AnimalRepository;
import io.redspark.repository.ConsultaRepository;
import io.redspark.repository.VacinaRepository;
import io.redspark.utils.MapperUtils;
import io.redspark.utils.SQLLikeUtils;

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

	private MapperUtils<Agendamento, AgendamentoDTO> convert = new MapperUtils<Agendamento, AgendamentoDTO>(Agendamento.class, AgendamentoDTO.class);

	@Transactional(readOnly = true)
	public Agendamento findOne(Long id) {
		return agendamentoRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Agendamento> findAll() {
		return agendamentoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Page<AgendamentoDTO> findAll(Pageable page, String search) {

		Page<Agendamento> result;

		if (StringUtils.hasText(search)) {
			result = agendamentoRepository.search(SQLLikeUtils.like(search), page);
		} else {
			result = agendamentoRepository.findAll(page);
		}

		List<AgendamentoDTO> agendamentos = result.getContent()
				.stream()
				.map(c -> convert.toDTO(c))
				.collect(Collectors.toList());

		return new PageImpl<AgendamentoDTO>(agendamentos, page, result.getTotalElements());
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
	public AgendamentoDTO update(Long id, AgendamentoForm agendamentoForm) {

		if (isNull(id) || isNull(agendamentoForm) || isNull(agendamentoForm.getData())) {
			throw new WebException(NOT_FOUND, "Valores inválidos");
		}

		Agendamento agendamento = agendamentoRepository.findOne(id);
		Animal animal = animalRepository.findOne(agendamentoForm.getIdAnimal());
		Consulta consulta = consultaRepository.findOne(agendamentoForm.getIdConsulta());
		Vacina vacina = vacinaRepository.findOne(agendamentoForm.getIdVacina());

		if (isNull(consulta) || isNull(animal) || isNull(vacina) || isNull(agendamento)) {
			throw new WebException(NOT_FOUND, "Valores inválidos");
		}

		agendamento.setAnimal(animal);
		agendamento.setConsulta(consulta);
		agendamento.setVacina(vacina);
		agendamento.setData(agendamentoForm.getData());

		agendamentoRepository.save(agendamento);

		return convert.toDTO(agendamento);
	}

	@Transactional
	public AgendamentoDTO delete(Long id) {

		if (isNull(id)) {
			throw new NotFoundException(Agendamento.class);
		}

		Agendamento agendamento = agendamentoRepository.findOne(id);

		if (isNull(agendamento)) {
			throw new NotFoundException(Agendamento.class);
		} else {
			agendamentoRepository.delete(agendamento);
		}

		return convert.toDTO(agendamento);
	}

}
