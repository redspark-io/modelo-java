package io.redspark.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class DefaultExceptionResolver implements HandlerExceptionResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionResolver.class);

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
      Exception ex) {
    ModelAndView modelAndView = null;

    if (ex.getClass().isAssignableFrom(BindException.class)) {
      BindException bindex = (BindException) ex;
      final StringBuilder message = new StringBuilder();
      bindex.getAllErrors().forEach(a -> message.append(a.getDefaultMessage()).append("\n"));
      try {
        response.sendError(HttpStatus.BAD_REQUEST.value(), message.toString());
        modelAndView = new ModelAndView();
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
        modelAndView = null;
      }
    } else if (ex instanceof WebException) {
      WebException webex = (WebException) ex;
      try {
        response.sendError(webex.getStatus().value(), ex.getMessage());
        modelAndView = new ModelAndView();
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
        modelAndView = null;
      }
    }

    return modelAndView;
  }
}