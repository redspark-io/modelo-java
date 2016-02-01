package io.redspark;

import io.redspark.exception.WebException;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@PropertySources({
    @PropertySource("application.properties"),
    @PropertySource(value = "${MODELO_CONFIG_PATH}", ignoreResourceNotFound = true) })
@SpringBootApplication
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableScheduling
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Application.class).run(args);
	}

	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		    .addResourceHandler("**/*.css", "**/*.js", "**/*.map", "*.html", "**/*.png", "**/*.ico", "**/*.jpeg",
		        "**/*.jpg")
		    .addResourceLocations("classpath:/static/").setCachePeriod(0);
		super.addResourceHandlers(registry);
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new HandlerExceptionResolver() {

			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			    Exception ex) {
				if (ex.getClass().isAssignableFrom(BindException.class)) {
					BindException bindex = (BindException) ex;
					final StringBuilder message = new StringBuilder();
					for(ObjectError a : bindex.getAllErrors())
					{
						message.append(a.getDefaultMessage()).append("\n");
					}
					try {
						response.sendError(HttpStatus.BAD_REQUEST.value(), message.toString());
					} catch (IOException e) {
						return null;
					}
					return new ModelAndView();
				}
				if (ex instanceof WebException) {
					WebException webex = (WebException) ex;
					try {
						response.sendError(webex.getStatus().value(), ex.getMessage());
					} catch (IOException e) {
						return null;
					}
					return new ModelAndView();
				}
				return null;
			}
		});
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}
}