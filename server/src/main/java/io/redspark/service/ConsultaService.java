package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.redspark.controller.form.ConsultaForm;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Veterinario;
import io.redspark.exception.WebException;
import io.redspark.repository.AnimalRepository;
import io.redspark.repository.ConsultaRepository;
import io.redspark.repository.VeterinarioRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private VeterinarioRepository veterinarioRepository;

	@Transactional(readOnly = true)
	public Consulta findOne(Long id) {
		return consultaRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Consulta> findAll() {
		return consultaRepository.findAll();
	}

	@Transactional
	public Consulta insert(ConsultaForm consultaForm) {

		if (isNull(consultaForm)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Animal animal = animalRepository.findOne(consultaForm.getIdAnimal());
		Veterinario veterinario = veterinarioRepository.findOne(consultaForm.getIdVeterinario());

		if (isNull(consultaForm.getDateConsulta()) || isNull(animal) || isNull(veterinario)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Consulta consulta = Consulta.builder().dateConsulta(consultaForm.getDateConsulta()).build();

		return consultaRepository.save(consulta);
	}

	@Transactional
	public Consulta update(Long id, ConsultaForm consultaForm) {

		if (isNull(id) || isNull(consultaForm) || isNull(consultaForm.getDateConsulta())) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Consulta consulta = consultaRepository.findOne(id);
		Animal animal = animalRepository.findOne(consultaForm.getIdAnimal());
		Veterinario veterinario = veterinarioRepository.findOne(consultaForm.getIdVeterinario());

		if (isNull(consulta) || isNull(animal) || isNull(veterinario)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		consulta.setAnimal(animal);
		consulta.setVeterinario(veterinario);
		consulta.setDateConsulta(consultaForm.getDateConsulta());

		consultaRepository.save(consulta);

		return consulta;
	}

	@Transactional
	public HttpStatus delete(Long id) {

		if (isNull(id)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		HttpStatus retorno;
		Consulta consulta = consultaRepository.findOne(id);

		if (isNull(consulta)) {
			retorno = HttpStatus.NOT_FOUND;
		} else {
			consultaRepository.delete(consulta);
			retorno = HttpStatus.OK;
		}

		return retorno;
	}

}
