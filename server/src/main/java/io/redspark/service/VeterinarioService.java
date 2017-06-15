package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.redspark.controller.form.VeterinarioForm;
import io.redspark.domain.vet.Veterinario;
import io.redspark.exception.WebException;
import io.redspark.repository.VeterinarioRepository;

@Service
public class VeterinarioService {

	@Autowired
	private VeterinarioRepository veterinarioRepository;

	@Transactional(readOnly = true)
	public Veterinario findOne(Long id) {
		return veterinarioRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Veterinario> findAll() {
		return veterinarioRepository.findAll();
	}

	@Transactional
	public Veterinario insert(VeterinarioForm veterinarioForm) {

		if (isNull(veterinarioForm)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Veterinario veterinario = Veterinario.builder().nome(veterinarioForm.getNome()).build();

		return veterinarioRepository.save(veterinario);
	}

	@Transactional
	public Veterinario update(Long id, VeterinarioForm veterinarioForm) {

		if (isNull(id) || isNull(veterinarioForm) || isNull(veterinarioForm.getNome())) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Veterinario veterinario = veterinarioRepository.findOne(id);

		if (isNull(veterinario)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		veterinario.setNome(veterinarioForm.getNome());

		veterinarioRepository.save(veterinario);

		return veterinario;
	}

	@Transactional
	public HttpStatus delete(Long id) {

		if (isNull(id)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		HttpStatus retorno;
		Veterinario veterinario = veterinarioRepository.findOne(id);

		if (isNull(veterinario)) {
			retorno = HttpStatus.NOT_FOUND;
		} else {
			veterinarioRepository.delete(veterinario);
			retorno = HttpStatus.OK;
		}

		return retorno;
	}

}
