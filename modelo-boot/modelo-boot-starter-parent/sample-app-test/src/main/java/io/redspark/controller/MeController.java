package io.redspark.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.org.sesc.commons.security.SescUser;
import io.redspark.controller.dto.UserDTO;

@RestController
@RequestMapping("me")
public class MeController {

	@GetMapping
	@ResponseBody
	public UserDTO me() {
		SescUser user = getUserLogged();

		return new UserDTO(user);
	}

	private SescUser getUserLogged() {
		return (SescUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
