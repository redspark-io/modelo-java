package io.redspark.controller;

import static io.redspark.controller.ControllerConstants.ME;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.dto.UserDTO;
import io.redspark.exception.WebException;
import io.redspark.security.DefaultUser;
import io.redspark.security.UserUtils;

@RestController
@RequestMapping(ME)
public class MeController {

  @GetMapping
  @ResponseBody
  public UserDTO me(@AuthenticationPrincipal DefaultUser principal) {

    if (UserUtils.isUserLoggedAsHolmesUser(principal)) {
      return new UserDTO(principal);
    }

    throw new WebException(HttpStatus.UNAUTHORIZED, "unauthorized");
  }

}
