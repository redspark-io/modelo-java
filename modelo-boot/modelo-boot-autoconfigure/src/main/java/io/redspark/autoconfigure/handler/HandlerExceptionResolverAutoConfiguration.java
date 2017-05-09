package io.redspark.autoconfigure.handler;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import io.redspark.commons.exception.WebException;
import lombok.extern.log4j.Log4j;

@Log4j
@Configuration
@ConditionalOnProperty(value = "modelo.boot.exception.handler.enabled", havingValue = "true", matchIfMissing = true)
public class HandlerExceptionResolverAutoConfiguration {
	
  @Bean
  @ConditionalOnMissingBean
  public HttpPutFormContentFilter httpPutFormContentFilter() {
    return new HttpPutFormContentFilter();
  }
	
	@Bean
	@ConditionalOnMissingBean
	public HandlerExceptionResolver handlerExceptionResolver() {
		return (request, response, handler, ex) -> {
			ModelAndView modelAndView = null;

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
			}

			return modelAndView;
		};
	}
}
