package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.redspark.controller.form.DonoForm;
import io.redspark.domain.vet.Dono;
import io.redspark.exception.WebException;
import io.redspark.repository.DonoRepository;

@Service
public class DonoService {

	@Autowired
	private DonoRepository donoRepository;

	@Transactional(readOnly = true)
	public Dono findOne(Long id) {
		return donoRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Dono> findAll() {
		return donoRepository.findAll();
	}

	@Transactional
	public Dono insert(DonoForm donoForm) {

		if (isNull(donoForm)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}
		Dono dono = null;

		if (Objects.isNull(donoForm.getEmail())) {
			dono = Dono.builder().nome(donoForm.getNome()).build();
		} else {
			dono = Dono.builder().nome(donoForm.getNome()).email(donoForm.getEmail()).build();
		}

		return donoRepository.save(dono);
	}

	@Transactional
	public Dono update(Long id, DonoForm donoForm) {

		if (isNull(id) || isNull(donoForm) || isNull(donoForm.getNome())) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Dono dono = donoRepository.findOne(id);

		if (isNull(dono)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		dono.setNome(donoForm.getNome());
		dono.setEmail(donoForm.getEmail());

		donoRepository.save(dono);

		return dono;
	}

	@Transactional
	public HttpStatus delete(Long id) {

		if (isNull(id)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		HttpStatus retorno;
		Dono dono = donoRepository.findOne(id);

		if (isNull(dono)) {
			retorno = HttpStatus.NOT_FOUND;
		} else {
			donoRepository.delete(dono);
			retorno = HttpStatus.OK;
		}

		return retorno;
	}

}
