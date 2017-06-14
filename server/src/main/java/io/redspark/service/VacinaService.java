package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.redspark.controller.form.VacinaForm;
import io.redspark.domain.vet.Vacina;
import io.redspark.exception.WebException;
import io.redspark.repository.VacinaRepository;

@Service
public class VacinaService {

	@Autowired
	private VacinaRepository vacinaRepository;

	@Transactional(readOnly = true)
	public Vacina findOne(Long id) {
		return vacinaRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<Vacina> findAll() {
		return vacinaRepository.findAll();
	}

	@Transactional
	public Vacina insert(VacinaForm vacinaForm) {

		if (isNull(vacinaForm)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Vacina vacina = Vacina.builder().nome(vacinaForm.getNome()).build();

		return vacinaRepository.save(vacina);
	}

	@Transactional
	public Vacina update(Long id, VacinaForm vacinaForm) {

		if (isNull(id) || isNull(vacinaForm) || isNull(vacinaForm.getNome())) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		Vacina vacina = vacinaRepository.findOne(id);

		if (isNull(vacina)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		vacina.setNome(vacinaForm.getNome());

		vacinaRepository.save(vacina);

		return vacina;
	}

	@Transactional
	public HttpStatus delete(Long id) {

		if (isNull(id)) {
			throw new WebException(BAD_REQUEST, "Valores inválidos");
		}

		HttpStatus retorno;
		Vacina vacina = vacinaRepository.findOne(id);

		if (isNull(vacina)) {
			retorno = HttpStatus.NOT_FOUND;
		} else {
			vacinaRepository.delete(vacina);
			retorno = HttpStatus.OK;
		}

		return retorno;
	}

}
