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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.org.sesc.commons.security.CustomLogoutSuccessHandler;
import br.org.sesc.commons.security.SescWebServiceAuthenticationProvider;
import br.org.sesc.commons.security.SescWebServiceAuthenticationSecurityFilter;

@Configuration
@ConditionalOnClass({ SescWebServiceAuthenticationSecurityFilter.class })
@ConditionalOnMissingBean({WebSecurityConfiguration.class})
@AutoConfigureAfter(value = {SescDaoAuthenticationProviderAutoConfiguration.class, 
		SescUserAuthenticationProviderAutoConfiguration.class})
@ConditionalOnProperty(prefix = "sesc.authentication.security", name = "enabled", havingValue = "true")
@ConditionalOnWebApplication
public class SescSecurityAutoConfiguration {
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	@ConditionalOnProperty(prefix = "sesc.authentication.security", name = "enabled", havingValue = "true")
	protected static class ApplicationWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
	  @Value("#{'${sesc.authentication.ant.ignored.matchers}'.split(',')}")
		private String[] antIgnoredMatchers;
			
		public SescWebServiceAuthenticationSecurityFilter sescWebServiceAuthenticationSecurityFilter() throws Exception {
			return new SescWebServiceAuthenticationSecurityFilter(authenticationManager(), authenticationEntryPoint());
		}
		
		@Autowired
		public void setAuthenticationManagerBuilder(AuthenticationManagerBuilder auth,
		    @Qualifier("sescUserAuthenticationProvider") SescWebServiceAuthenticationProvider sescWebServiceAuthenticationProvider,
		    @Qualifier("daoAuthenticationProvider") DaoAuthenticationProvider daoAuthenticationProvider) {
			auth.authenticationProvider(sescWebServiceAuthenticationProvider);
			auth.authenticationProvider(daoAuthenticationProvider);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers(antIgnoredMatchers).permitAll().anyRequest().fullyAuthenticated();
			this.configureCsrf(http);
			this.configureSession(http);
			this.configureEntryPoint(http);
			this.configureFilters(http);
			this.congigureLogout(http);
		}

		private void congigureLogout(HttpSecurity http) throws Exception {
			http.logout().logoutSuccessHandler(new CustomLogoutSuccessHandler()).invalidateHttpSession(true);
		}

		private void configureFilters(HttpSecurity http) throws Exception {
			http.addFilterBefore(sescWebServiceAuthenticationSecurityFilter(), BasicAuthenticationFilter.class);
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
				public void commence(HttpServletRequest request, HttpServletResponse response,
				    AuthenticationException authException) throws IOException, ServletException {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
				}
			};
		}
	}
}
