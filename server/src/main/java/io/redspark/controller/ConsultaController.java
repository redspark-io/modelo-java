package io.redspark.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.form.ConsultaForm;
import io.redspark.domain.vet.Consulta;
import io.redspark.service.ConsultaService;

@RestController
@RequestMapping(value = ControllerConstants.CONSULTA)
public class ConsultaController {

	@Autowired
	private ConsultaService consultaService;

	@RequestMapping(method = GET, value = "{id}")
	public Consulta find(@PathVariable Long id) {
		return consultaService.findOne(id);
	}

	@RequestMapping(method = GET)
	public List<Consulta> findAll() {
		return consultaService.findAll();
	}

	@RequestMapping(method = POST)
	public Consulta insert(@RequestBody ConsultaForm consultaForm) {
		return consultaService.insert(consultaForm);
	}

	@RequestMapping(method = PUT, value = "{id}")
	public Consulta update(@PathVariable Long id, ConsultaForm consultaForm) {
		return consultaService.update(id, consultaForm);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public HttpStatus delete(@PathVariable Long id) {
		return consultaService.delete(id);
	}

}
