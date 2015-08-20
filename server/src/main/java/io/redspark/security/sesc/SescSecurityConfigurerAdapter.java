package io.redspark.security.sesc;

import io.redspark.service.PermissaoService;
import io.redspark.service.PermissaoServiceTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import br.org.sesc.administrativo.client.MatriculaClientService;
import br.org.sesc.administrativo.client.UnidadeOrcamentariaClientService;
import br.org.sesc.administrativo.client.UsuarioClientService;
import br.org.sesc.administrativo.client.rest.security.AuthenticatedRestTemplate;
import br.org.sesc.administrativo.client.service.impl.MatriculaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.UnidadeOrcamentariaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.UsuarioClientServiceImpl;
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
import br.org.sesc.permissao.sync.SyncService;
import br.org.sesc.permissao.sync.annotation.AnnotatedControllerPermissionLoader;
import br.org.sesc.permissao.sync.config.DefaultSyncConfiguration;
import br.org.sesc.permissao.sync.xml.XmlPermissionLoader;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SescSecurityConfigurerAdapter extends
GlobalAuthenticationConfigurerAdapter {

	@Value("${sesc.authentication.url}")
	private String authenticationUrl;

	@Value("${sesc.authentication.app.codigo}")
	private Long codigo;

	@Value("${sesc.administrativo.user}")
	private String user;

	@Value("${sesc.administrativo.password}")
	private String password;

	@Value("${sesc.administrativo.host}")
	private String host;

	/**
	 * PERMISSÃO PROPERTIES
	 */
	@Value("${sesc.administrativo.permissao-url}")
	private String permissaoUrl;

	@Value("${sesc.administrativo.permissao-hash}")
	private String permissaoHash;

	@Value("${sesc.administrativo.permissao-login}")
	private String permissaoLogin;

	@Value("${sesc.administrativo.permissao-password}")
	private String permissaoPassword;

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

		List<SecurityValidation> list = new ArrayList<SecurityValidation>();
		list.add(hashSecurityValidation);
		return list;
	}

	// *****************************************
	// ************* HOOK
	// *****************************************

	@Bean
	@SuppressWarnings("rawtypes")
	@Profile({"DEV", "QA", "PRODUCAO"})
	public AuthenticationHook hook(PermissaoService service) {
		return new SescAuthenticationHook(service);
	}

	// *****************************************
	// ************* PROVIDERS
	// *****************************************

	/**
	 * Provider de autenticação do usuário no SESC
	 */
	@Bean
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SescWebServiceAuthenticationProvider sescWebServiceAuthenticationProvider(AuthenticationHook hook) {

		SescWebServiceAuthenticationProvider authenticationProvider = new SescWebServiceAuthenticationProvider(
				CustomSescUser.class);
		authenticationProvider.setValidations(sescAuthenticationValidators());
		authenticationProvider.setHook(hook);

		return authenticationProvider;
	}

	/**
	 * Provider de autenticação da aplicação
	 */
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		ArrayList<SescUser> userAuthList = new ArrayList<SescUser>();
		for (int i = 0; i < this.sescApplicationUser.size(); i++) {
			userAuthList.add(this.sescApplicationUser.getSescUser(i));
		}

		SimpleUserDetailService simpleUserDetailService = new SimpleUserDetailService(userAuthList);
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(simpleUserDetailService);

		return daoAuthenticationProvider;

	}

	// *****************************************
	// ************* MANAGER
	// *****************************************

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		super.init(auth);
	}

	@Autowired
	public void setAuthenticationManagerBuilder(AuthenticationManagerBuilder auth, @Qualifier("sescWebServiceAuthenticationProvider") SescWebServiceAuthenticationProvider sescWebServiceAuthenticationProvider, @Qualifier("daoAuthenticationProvider") DaoAuthenticationProvider daoAuthenticationProvider) {
		auth.authenticationProvider(sescWebServiceAuthenticationProvider);
		auth.authenticationProvider(daoAuthenticationProvider);
	}

	// *****************************************
	// ************* Security
	// *****************************************

	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		public SescWebServiceAuthenticationSecurityFilter sescWebServiceAuthenticationSecurityFilter() throws Exception {
			return new SescWebServiceAuthenticationSecurityFilter(authenticationManager(), authenticationEntryPoint());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/styles/**/*", "/i18n/**/*", "/fonts/**/*", "/assets/**/*", "/scripts/**/*", "/index.html","/version.html","/404.html").permitAll().anyRequest().fullyAuthenticated();
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
				public void commence(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException authException)
								throws IOException, ServletException {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
				}
			};
		}
	}

	@Bean
	public AuthenticatedRestTemplate authenticatedRestTemplate() {
		return new AuthenticatedRestTemplate(user, password);
	}

	@Bean
	public UnidadeOrcamentariaClientService unidadeOrcamentariaClientService() {
		return new UnidadeOrcamentariaClientServiceImpl(host, authenticatedRestTemplate());
	}

	@Bean
	public MatriculaClientService matriculaClientService() {
		return new MatriculaClientServiceImpl(host, authenticatedRestTemplate());
	}

	@Bean
	public UsuarioClientService usuarioClientService() {
		return new UsuarioClientServiceImpl(host, authenticatedRestTemplate());
	}

	// *****************************************
	// ************** Permissoes ***************
	// *****************************************
	@Bean
	public DefaultSyncConfiguration defaultSyncConfiguration() {
		DefaultSyncConfiguration sync = new DefaultSyncConfiguration();
		sync.setHash(permissaoHash);
		sync.setLogin(permissaoLogin);
		sync.setPassword(permissaoPassword);
		sync.setUrl(permissaoUrl);
		return sync;
	}

	@Bean
	@Profile(value={"QA", "PRODUCAO"})
	public AnnotatedControllerPermissionLoader annotationPermissionLoader() {
		AnnotatedControllerPermissionLoader acpl = new AnnotatedControllerPermissionLoader();
		return acpl;
	}

	@Bean
	@Profile(value={"QA", "PRODUCAO"})
	public XmlPermissionLoader xmlPermissionLoader() throws IOException, URISyntaxException {
		XmlPermissionLoader xml = new XmlPermissionLoader();

		URL url = this.getClass().getResource("/permissoes.xml");
		FileSystemResource fsr = new FileSystemResource(new File(url.toURI()));

		xml.setFile(fsr);
		return xml;
	}

	@Bean
	@Profile(value={"QA", "PRODUCAO"})
	public SyncService syncService(DefaultSyncConfiguration dsc, AnnotatedControllerPermissionLoader acpl, XmlPermissionLoader xpl) {
		SyncService syncService = new SyncService(dsc);
		syncService.setLoaders(Arrays.asList(acpl, xpl));
		return syncService;
	}

	@Bean
	public PermissaoService permissaoServiceClient(DefaultSyncConfiguration dsc) {
		return new PermissaoService(dsc);
	}

	/******************************************
	 ********* Servicos para test **********
	 *****************************************/

	@Bean
	@Profile({"test-integration"})
	public PermissaoService permissaoServiceClientTest(DefaultSyncConfiguration dsc) {
		return new PermissaoServiceTest(dsc);
	}

	@Bean
	@Profile({"test-integration"})
	@SuppressWarnings("rawtypes")
	public AuthenticationHook hookTest() {
		return new SescAuthenticationHookTest();
	}
}
