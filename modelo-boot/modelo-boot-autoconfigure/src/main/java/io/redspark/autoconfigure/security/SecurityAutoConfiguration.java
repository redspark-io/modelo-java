package io.redspark.autoconfigure.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.redspark.commons.security.DefaultFailureHandler;
import io.redspark.commons.security.DefaultSuccessHandler;

@Configuration
@ConditionalOnWebApplication
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ConditionalOnProperty(prefix = "modelo.boot.security", name = "enabled", havingValue = "true")
public class SecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Value("#{'${authentication.ant.ignored.matchers}'.split(',')}")
  protected String[] antIgnoredMatchers;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers(antIgnoredMatchers).permitAll().anyRequest().fullyAuthenticated();
    this.configureCsrf(http);
    this.configureSession(http);
    this.configureEntryPoint(http);
    this.configureAuthentication(http);
  }

  protected void configureAuthentication(HttpSecurity http) throws Exception {
    DefaultSuccessHandler successHandler = new DefaultSuccessHandler(null); // TODO

    http.formLogin().loginProcessingUrl("/login").successHandler(successHandler)
        .failureHandler(new DefaultFailureHandler());

    http.logout().logoutUrl("/logout").logoutSuccessHandler(successHandler);
  }

  protected void configureCsrf(HttpSecurity http) throws Exception {
    http.csrf().disable();
  }

  protected void configureSession(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
  }

  protected void configureEntryPoint(HttpSecurity http) throws Exception {
    http.exceptionHandling().authenticationEntryPoint((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized"));
  }
}
