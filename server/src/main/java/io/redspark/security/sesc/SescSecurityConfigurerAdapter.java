package io.redspark.security.sesc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.commons.security.SescUser;
import br.org.sesc.commons.security.SescWebServiceAuthenticationProvider;
import br.org.sesc.commons.security.SescWebServiceAuthenticationSecurityFilter;
import br.org.sesc.commons.security.test.SimpleUserDetailService;
import br.org.sesc.commons.security.validation.HashSecurityValidation;
import br.org.sesc.commons.security.validation.SecurityValidation;
import br.org.sesc.commons.security.webservice.AuthenticationMessage;
import br.org.sesc.commons.security.webservice.AuthenticationResponse;
import br.org.sesc.commons.security.webservice.AuthenticationResult;
import br.org.sesc.commons.security.webservice.SescAuthentication;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SescSecurityConfigurerAdapter extends
    GlobalAuthenticationConfigurerAdapter {

	@Value("${sesc.authentication.url}")
	private String authenticationUrl;

	@Value("${sesc.authentication.app.codigo}")
	private Long codigo;

	@Autowired
	private SescApplicationUser sescApplicationUser;

	// *****************************************
	// ************* WEB SERVICES
	// *****************************************
	@Bean
	public WebServiceMessageFactory messageFactory() {
		return new SaajSoapMessageFactory();
	}

	@Bean
	public WebServiceTemplate securityWebService() {

		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(AuthenticationMessage.class,
		    SescAuthentication.class, AuthenticationResult.class,
		    AuthenticationResponse.class);

		WebServiceTemplate template = new WebServiceTemplate(messageFactory());
		template.setMarshaller(marshaller);
		template.setUnmarshaller(marshaller);
		template.setDefaultUri(authenticationUrl);

		return template;
	}

	// *****************************************
	// ************* VALIDATORS
	// *****************************************
	@Bean
	public List<SecurityValidation> sescAuthenticationValidators() {
		HashSecurityValidation hashSecurityValidation = new HashSecurityValidation();
		hashSecurityValidation.setCodigo(codigo);

		return Arrays.asList(hashSecurityValidation);
	}

	// *****************************************
	// ************* HOOK
	// *****************************************

	@Bean
	@SuppressWarnings("rawtypes")
	public AuthenticationHook hook() {
		return new SampleAuthenticationHook();
	}

	// *****************************************
	// ************* PROVIDERS
	// *****************************************

	/**
	 * Provider de autenticação do usuário no SESC
	 */
	@Bean
	@SuppressWarnings("unchecked")
	public SescWebServiceAuthenticationProvider sescUserAuthenticationProvider() {

		SescWebServiceAuthenticationProvider authenticationProvider = new SescWebServiceAuthenticationProvider(
		    CustomSescUser.class);
		authenticationProvider.setValidations(sescAuthenticationValidators());
		authenticationProvider.setHook(hook());

		return authenticationProvider;
	}

	/**
	 * Provider de autenticação da aplicação
	 */
	public DaoAuthenticationProvider applicationAuthenticationProvider() {
		ArrayList<SescUser> userAuthList = new ArrayList<SescUser>();
		for (int i = 0; i < this.sescApplicationUser.size(); i++) {
			userAuthList.add(this.sescApplicationUser.getSescUser(i));
		}

		SimpleUserDetailService simpleUserDetailService = new SimpleUserDetailService(userAuthList);
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(simpleUserDetailService);

		return authenticationProvider;

	}

	// *****************************************
	// ************* MANAGER
	// *****************************************

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(sescUserAuthenticationProvider());
		auth.authenticationProvider(applicationAuthenticationProvider());
		super.init(auth);
	}

	// *****************************************
	// ************* Security
	// *****************************************

	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends
	    WebSecurityConfigurerAdapter {

		public SescWebServiceAuthenticationSecurityFilter sescWebServiceAuthenticationSecurityFilter()
		    throws Exception {
			return new SescWebServiceAuthenticationSecurityFilter(
			    authenticationManager());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/public").permitAll()
			    .anyRequest().fullyAuthenticated();
			this.configureCsrf(http);
			this.configureSession(http);
			this.configureEntryPoint(http);
			this.configureAuthentication(http);
			this.configureFilters(http);
		}

		private void configureFilters(HttpSecurity http) throws Exception {
			http.addFilterBefore(sescWebServiceAuthenticationSecurityFilter(),
			    BasicAuthenticationFilter.class);
		}

		private void configureAuthentication(HttpSecurity http)
		    throws Exception {
			http.httpBasic();
		}

		private void configureCsrf(HttpSecurity http) throws Exception {
			http.csrf().disable();
		}

		private void configureSession(HttpSecurity http) throws Exception {
			http.sessionManagement().sessionCreationPolicy(
			    SessionCreationPolicy.ALWAYS);
		}

		private void configureEntryPoint(HttpSecurity http) throws Exception {
			http.exceptionHandling().authenticationEntryPoint(
			    (request, response, exception) -> response
			        .sendError(HttpServletResponse.SC_UNAUTHORIZED,
			            "unauthorized"));
		}
	}
}
