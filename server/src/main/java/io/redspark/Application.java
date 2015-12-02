package io.redspark;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.validation.BindException;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.redspark.controller.dto.UserDTO;
import io.redspark.exception.WebException;
import io.redspark.security.DefaultFailureHandler;
import io.redspark.security.DefaultSuccessHandler;
import io.redspark.security.DefaultUser;
import io.redspark.security.UserLoginService;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Application extends WebMvcConfigurerAdapter {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(Application.class).run(args);
  }

  @Bean
  public ApplicationSecurity applicationSecurity() {
    return new ApplicationSecurity();
  }

  @Bean
  public HttpPutFormContentFilter httpPutFormContentFilter() {
    return new HttpPutFormContentFilter();
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
          bindex.getAllErrors().forEach(a -> message.append(a.getDefaultMessage()).append("\n"));
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

  @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
  protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userLoginService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().antMatchers("/public").permitAll().anyRequest().fullyAuthenticated();
      this.configureCsrf(http);
      this.configureSession(http);
      this.configureEntryPoint(http);
      this.configureAuthentication(http);
    }

    private void configureAuthentication(HttpSecurity http) throws Exception {
      DefaultSuccessHandler successHandler = new DefaultSuccessHandler(p -> new UserDTO((DefaultUser) p));

      http.formLogin().loginProcessingUrl("/login").successHandler(successHandler)
          .failureHandler(new DefaultFailureHandler());

      http.logout().logoutUrl("/logout").logoutSuccessHandler(successHandler);
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
      http.csrf().disable();
    }

    private void configureSession(HttpSecurity http) throws Exception {
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

    private void configureEntryPoint(HttpSecurity http) throws Exception {
      http.exceptionHandling().authenticationEntryPoint(
          (request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized"));
    }
  }

}
