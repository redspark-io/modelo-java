package io.redspark;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.redspark.exception.DefaultExceptionResolver;

@Configuration
@Import(SecurityConfig.class)
public class ApplicationConfig {

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