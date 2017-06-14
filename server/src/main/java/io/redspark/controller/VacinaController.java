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

import io.redspark.controller.form.VacinaForm;
import io.redspark.domain.vet.Vacina;
import io.redspark.service.VacinaService;

@RestController
@RequestMapping(value = ControllerConstants.VACINA)
public class VacinaController {

	@Autowired
	private VacinaService vacinaService;

	@RequestMapping(method = GET, value = "{id}")
	public Vacina find(@PathVariable Long id) {
		return vacinaService.findOne(id);
	}

	@RequestMapping(method = GET)
	public List<Vacina> findAll() {
		return vacinaService.findAll();
	}

	@RequestMapping(method = POST)
	public Vacina insert(@RequestBody VacinaForm vacinaForm) {
		return vacinaService.insert(vacinaForm);
	}

	@RequestMapping(method = PUT, value = "{id}")
	public Vacina update(@PathVariable Long id, VacinaForm vacinaForm) {
		return vacinaService.update(id, vacinaForm);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public HttpStatus delete(@PathVariable Long id) {
		return vacinaService.delete(id);
	}
}
