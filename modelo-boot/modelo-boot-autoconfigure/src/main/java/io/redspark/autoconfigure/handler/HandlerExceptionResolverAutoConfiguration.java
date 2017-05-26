package io.redspark.autoconfigure.handler;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import java.io.IOException;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.redspark.commons.exception.WebException;
import lombok.extern.log4j.Log4j;

@Log4j
@Configuration
@ConditionalOnProperty(value = "modelo.boot.exception.handler.enabled", havingValue = "true", matchIfMissing = true)
public class HandlerExceptionResolverAutoConfiguration extends WebMvcConfigurerAdapter {

  @Autowired
  @Qualifier("modeloBootExceptionResover")
  private HandlerExceptionResolver handler;

  @Override
  public void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> exceptionResolvers) {

    exceptionResolvers.add(handler);

    super.configureHandlerExceptionResolvers(exceptionResolvers);
  }

  @Bean
  @ConditionalOnMissingBean
  public HttpPutFormContentFilter httpPutFormContentFilter() {
    return new HttpPutFormContentFilter();
  }

  @Bean
  @ConditionalOnMissingBean(name = "modeloBootExceptionResover")
  public HandlerExceptionResolver modeloBootExceptionResover() {
    return (request, response, handler, ex) -> {

      ModelAndView modelAndView = null;

      ex.printStackTrace();

      if (ex.getClass().isAssignableFrom(BindException.class)) {

        BindException bindex = (BindException) ex;

        final StringBuilder message = new StringBuilder();
        bindex.getAllErrors().forEach(a -> message.append(a.getDefaultMessage()).append("\n"));

        try {
          response.sendError(HttpStatus.BAD_REQUEST.value(), message.toString());
          modelAndView = new ModelAndView();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      } else if (ex instanceof WebException) {

        WebException webex = (WebException) ex;

        try {
          response.sendError(webex.getStatus().value(), ex.getMessage());
          modelAndView = new ModelAndView();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      } else if (ex instanceof ConstraintViolationException) {

        ConstraintViolationException cex = (ConstraintViolationException) ex;

        try {
          response.sendError(PRECONDITION_FAILED.value(), cex.getMessage());
          modelAndView = new ModelAndView();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }

      return modelAndView;
    };
  }
}
