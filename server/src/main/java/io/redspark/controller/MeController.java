package io.redspark.controller;

import io.redspark.controller.dto.UserDTO;
import io.redspark.exception.WebException;
import io.redspark.security.UserUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.org.sesc.commons.security.SescUser;

@RestController
@RequestMapping("/me")
public class MeController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public UserDTO me() {

		SescUser userLogged = UserUtils.getUserLogged();

		if (userLogged != null) {
			return new UserDTO(userLogged);
		}

		throw new WebException(HttpStatus.UNAUTHORIZED, "unauthorized");
	}

}
