package io.redspark;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@Import(ApplicationConfig.class)
public class Application extends WebMvcConfigurerAdapter {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(Application.class).run(args);
  }
}