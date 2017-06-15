package io.redspark.controller;

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

import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.VeterinarioDTO;
import io.redspark.controller.form.VeterinarioForm;
import io.redspark.domain.vet.Veterinario;
import io.redspark.service.VeterinarioService;

@RestController
@RequestMapping(value = ControllerConstants.VETERINARIO)
public class VeterinarioController {

	@Autowired
	private VeterinarioService veterinarioService;

	@RequestMapping(method = GET, value = "{id}")
	public Veterinario find(@PathVariable Long id) {
		return veterinarioService.findOne(id);
	}

	@RequestMapping(method = GET)
	public List<VeterinarioDTO> findAll() {
		return veterinarioService.findAll().stream().map(VeterinarioDTO::new).collect(Collectors.toList());
	}

	@RequestMapping(method = POST)
	public Veterinario insert(@RequestBody VeterinarioForm veterinarioForm) {
		return veterinarioService.insert(veterinarioForm);
	}

	@RequestMapping(method = PUT, value = "{id}")
	public Veterinario update(@PathVariable Long id, @RequestBody VeterinarioForm veterinarioForm) {
		return veterinarioService.update(id, veterinarioForm);
	}

	@RequestMapping(method = DELETE, value = "{id}")
	public HttpStatus delete(@PathVariable Long id) {
		return veterinarioService.delete(id);
	}
}
