package io.redspark.autoconfigure.sesc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import SescBasicAuthenticationFilter.SescBasicAuthenticationFilter;
import br.org.sesc.commons.security.CustomLogoutSuccessHandler;
import br.org.sesc.commons.security.SescWebServiceAuthenticationProvider;
import br.org.sesc.commons.security.SescWebServiceAuthenticationSecurityFilter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@ConditionalOnMissingBean({ WebSecurityConfiguration.class })
@ConditionalOnClass({ SescWebServiceAuthenticationSecurityFilter.class })
@AutoConfigureAfter(value = { SescDaoAuthenticationProviderAutoConfiguration.class, SescUserAuthenticationProviderAutoConfiguration.class })
@ConditionalOnProperty(prefix = "sesc.authentication.security", name = "enabled", havingValue = "true")
@ConditionalOnWebApplication
public class SescSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

  @Value("#{'${authentication.ant.ignored.matchers}'.split(',')}")
  protected String[] antIgnoredMatchers;

  @Autowired
  private AuthenticationManagerBuilder auth;

  public SescWebServiceAuthenticationSecurityFilter sescWebServiceAuthenticationSecurityFilter() throws Exception {
    return new SescWebServiceAuthenticationSecurityFilter(authenticationManager(), authenticationEntryPoint());
  }

  public SescBasicAuthenticationFilter sescBasicAuthenticationFilter() throws Exception {
    return new SescBasicAuthenticationFilter(authenticationManager());
  }

  @Autowired(required = false)
  @Qualifier("daoAuthenticationProvider")
  public void setDaoAuthenticationProvider(DaoAuthenticationProvider dao) {
    auth.authenticationProvider(dao);
  }

  @Autowired(required = false)
  @Qualifier("sescUserAuthenticationProvider")
  public void setSescWebServiceAuthenticationProvider(SescWebServiceAuthenticationProvider ws) {
    auth.authenticationProvider(ws);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .anyRequest()
        .fullyAuthenticated()
        .and()
        .authorizeRequests()
        .antMatchers(antIgnoredMatchers)
        .permitAll();

    this.configureCsrf(http);
    this.configureSession(http);
    this.configureEntryPoint(http);
    this.configureFilters(http);
    this.congigureLogout(http);
  }

  @Override
  public void configure(WebSecurity web) {
    web
        .ignoring()
        .antMatchers(antIgnoredMatchers);
  }

  protected void congigureLogout(HttpSecurity http) throws Exception {
    http.logout().logoutSuccessHandler(new CustomLogoutSuccessHandler()).invalidateHttpSession(true);
  }

  protected void configureFilters(HttpSecurity http) throws Exception {
    http.addFilter(sescBasicAuthenticationFilter());
    http.addFilterBefore(sescWebServiceAuthenticationSecurityFilter(), BasicAuthenticationFilter.class);
  }

  protected void configureCsrf(HttpSecurity http) throws Exception {
    http.csrf().disable();
  }

  protected void configureSession(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
  }

  protected void configureEntryPoint(HttpSecurity http) throws Exception {
    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
  }

  protected AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPoint() {

      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response,
          AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
      }
    };
  }
}
