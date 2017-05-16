package io.redspark;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class ApplicationConfig {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(ApplicationConfig.class).run(args);
	}

}