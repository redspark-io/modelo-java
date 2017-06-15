package io.redspark.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.AgendamentoDTO;
import io.redspark.controller.form.AgendamentoForm;
import io.redspark.domain.vet.Agendamento;
import io.redspark.service.AgendamentoService;

@RestController
@RequestMapping(value = ControllerConstants.AGENDAMENTO)
public class AgendamentoController {

	@Autowired
	private AgendamentoService agendamentoService;

	@RequestMapping(method = GET, value = "{id}")
	public Agendamento find(@PathVariable Long id) {
		return agendamentoService.findOne(id);
	}

	@RequestMapping(method = GET)
	public List<AgendamentoDTO> findAll() {
		return agendamentoService.findAll().stream().map(AgendamentoDTO::new).collect(Collectors.toList());
	}

	@RequestMapping(method = POST)
	public Agendamento insert(@RequestBody AgendamentoForm agendamentoForm) {
		return agendamentoService.insert(agendamentoForm);
	}

	@RequestMapping(method = PUT, value = "{id}")
	public Agendamento update(@PathVariable Long id, @RequestBody AgendamentoForm agendamentoForm) {
		return agendamentoService.update(id, agendamentoForm);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public HttpStatus delete(@PathVariable Long id) {
		return agendamentoService.delete(id);
	}

}
