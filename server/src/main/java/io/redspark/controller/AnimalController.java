package io.redspark.controller;

import static io.redspark.controller.ControllerConstants.ANIMAL;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.form.AnimalForm;
import io.redspark.domain.vet.Animal;
import io.redspark.service.AnimalService;

// No Spring temos o controller === Resource do JAX-RS
@RestController
// @RequestMapping === @Path & @VERB (@GET, @POST,...)
@RequestMapping(value = ANIMAL)
public class AnimalController {

	// Controller - Validações de form, param, em geral
	// Regra de negócio - Model ou Service
	// Retornar DTO e não Entidade

	@Autowired
	private AnimalService service;

	// @GET & @Path({id})
	@RequestMapping(method = GET, value = "{id}")
	public Animal find(@PathVariable Long id) {
		return service.findOne(id);
	}

	@RequestMapping(method = GET)
	public List<Animal> findAll() {
		return service.findAll();
	}

	@RequestMapping(method = POST)
	public Animal insert(@RequestBody AnimalForm form) {
		return service.insert(form);
	}

	@RequestMapping(method = PUT, value = "{id}")
	public Animal update(@PathVariable Long id, @RequestBody AnimalForm form) {
		return service.update(id, form);
	}

	@RequestMapping(method = DELETE, value = "{id}")
	public HttpStatus delete(@PathVariable Long id) {
		return service.delete(id);
	}
}
