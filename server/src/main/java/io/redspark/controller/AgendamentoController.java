package io.redspark.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.constant.ControllerConstants;
import io.redspark.controller.dto.AgendamentoDTO;
import io.redspark.controller.form.AgendamentoForm;
import io.redspark.domain.vet.Agendamento;
import io.redspark.exception.NotFoundException;
import io.redspark.service.AgendamentoService;
import io.redspark.utils.MapperUtils;

@RestController
@RequestMapping(value = ControllerConstants.AGENDAMENTO)
public class AgendamentoController {

	@Autowired
	private AgendamentoService agendamentoService;

	private MapperUtils<Agendamento, AgendamentoDTO> convert = new MapperUtils<Agendamento, AgendamentoDTO>(Agendamento.class, AgendamentoDTO.class);

	@GetMapping(value = "{id}")
	public AgendamentoDTO find(@PathVariable Long id) {
		Agendamento agendamento = agendamentoService.findOne(id);
		if (Objects.isNull(agendamento)) {
			throw new NotFoundException(Agendamento.class);
		}
		return convert.toDTO(agendamento);
	}

	@GetMapping
	public List<AgendamentoDTO> findAll() {
		return agendamentoService.findAll()
				.stream()
				.map(AgendamentoDTO::new)
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/page")
	public Page<AgendamentoDTO> findAll(@PageableDefault(page = 0, size = 50, sort = "animal.nome") Pageable page,
			@RequestParam(value = "search", required = false) String search) {
		return agendamentoService.findAll(page, search);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public AgendamentoDTO insert(@Valid @RequestBody AgendamentoForm agendamentoForm) {
		return convert.toDTO(agendamentoService.insert(agendamentoForm));
	}

	@PutMapping(value = "{id}")
	public AgendamentoDTO update(@PathVariable Long id, @RequestBody AgendamentoForm agendamentoForm) {
		return agendamentoService.update(id, agendamentoForm);
	}

	@DeleteMapping(value = "{id}")
	public AgendamentoDTO delete(@PathVariable Long id) {
		return agendamentoService.delete(id);
	}

}
