/**
 * 
 */
package io.redspark;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.org.sesc.commons.security.SescWebServiceAuthenticationSecurityFilter;

/**
 * @author GSuaki
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
	
	public SescWebServiceAuthenticationSecurityFilter sescWebServiceAuthenticationSecurityFilter() throws Exception {
		return new SescWebServiceAuthenticationSecurityFilter(authenticationManager(), authenticationEntryPoint());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/styles/**/*", "/i18n/**/*", "/fonts/**/*", "/assets/**/*", 
					"/scripts/**/*", "/index.html", "/version.html", "/404.html",
					"/swagger-ui.html", "/webjars/**", "/configuration/*", "/swagger-resources", "/swagger-resources/**/*", "/v2/api-docs")
			.permitAll()
			.anyRequest()
			.fullyAuthenticated();
		
		this.configureCsrf(http);
		this.configureSession(http);
		this.configureEntryPoint(http);
		this.configureAuthentication(http);
		this.configureFilters(http);
	}

	private void configureFilters(HttpSecurity http) throws Exception {
		http.addFilterBefore(sescWebServiceAuthenticationSecurityFilter(), BasicAuthenticationFilter.class);
	}

	private void configureAuthentication(HttpSecurity http) throws Exception {
		http.httpBasic();
	}

	private void configureCsrf(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}

	private void configureSession(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}

	private void configureEntryPoint(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
	}

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
			}
		};
	}
}
