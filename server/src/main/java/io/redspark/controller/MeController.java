package io.redspark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.dto.UserDTO;
import io.redspark.exception.WebException;
import io.redspark.security.UserUtils;

@RestController
@RequestMapping("/me")
public class MeController {

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public UserDTO me() {

    if (UserUtils.isUserLoggedAsHolmesUser()) {
      return new UserDTO(UserUtils.getUserLogged());
    }

    throw new WebException(HttpStatus.UNAUTHORIZED, "unauthorized");
  }

}
