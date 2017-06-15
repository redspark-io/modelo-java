package io.redspark.controller;

import static io.redspark.controller.constant.ControllerConstants.DONO;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
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
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.dto.DonoDTO;
import io.redspark.controller.form.DonoForm;
import io.redspark.domain.vet.Dono;
import io.redspark.service.DonoService;

@RestController
@RequestMapping(value = DONO)
public class DonoController {

	@Autowired
	private DonoService donoService;

	@RequestMapping(method = GET, value = "{id}")
	public Dono find(@PathVariable Long id) {
		return donoService.findOne(id);
	}

	@RequestMapping(method = GET)
	public List<DonoDTO> findAll() {
		return donoService.findAll().stream().map(DonoDTO::new).collect(Collectors.toList());
	}

	@RequestMapping(method = POST)
	public Dono insert(@RequestBody DonoForm donoForm) {
		return donoService.insert(donoForm);
	}

	@RequestMapping(method = PUT, value = "{id}")
	public Dono update(@PathVariable Long id, @RequestBody DonoForm donoForm) {
		return donoService.update(id, donoForm);
	}

	@RequestMapping(method = DELETE, value = "{id}")
	public HttpStatus delete(@PathVariable Long id) {
		return donoService.delete(id);
	}
}
