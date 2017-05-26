package io.redspark.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.org.sesc.commons.security.SescUser;
import io.redspark.commons.security.UserUtils;
import io.redspark.controller.dto.UserDTO;

@RestController
@RequestMapping("me")
public class MeController {

	@GetMapping
	@ResponseBody
	public UserDTO me() {
		SescUser user = (SescUser) UserUtils.getUserLogged();

		return new UserDTO(user);
	}
}
