package io.redspark;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import io.redspark.exception.DefaultExceptionResolver;

@Configuration
@Import(SecurityConfig.class)
public class ApplicationConfig {

	@Bean
	public ObjectMapper jsonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return Jackson2ObjectMapperBuilder.json().modules(new JSR310Module()).build();
	}

	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
				exceptionResolvers.add(new DefaultExceptionResolver());
				super.configureHandlerExceptionResolvers(exceptionResolvers);
			}
		};
	}
}