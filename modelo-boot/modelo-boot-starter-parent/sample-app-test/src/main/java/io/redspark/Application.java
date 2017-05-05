package io.redspark;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
//@EnableGlobalMethodSecurity(securedEnabled = true)
//@Import(ApplicationConfig.class)
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class Application {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(Application.class).run(args);
  }
}