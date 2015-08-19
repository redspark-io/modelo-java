package io.redspark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationServletInitializer.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationServletInitializer.class, args);
	}

}
