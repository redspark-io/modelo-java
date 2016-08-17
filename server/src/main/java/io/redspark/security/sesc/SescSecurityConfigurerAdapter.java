package io.redspark.security.sesc;

import static io.redspark.AppProfile.DEV;
import static io.redspark.AppProfile.PRODUCAO;
import static io.redspark.AppProfile.QA;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.commons.security.SescUser;
import br.org.sesc.commons.security.SescWebServiceAuthenticationProvider;
import br.org.sesc.commons.security.test.SimpleUserDetailService;
import br.org.sesc.commons.security.validation.HashSecurityValidation;
import br.org.sesc.commons.security.validation.SecurityValidation;
import br.org.sesc.commons.security.webservice.AuthenticationMessage;
import br.org.sesc.commons.security.webservice.AuthenticationResponse;
import br.org.sesc.commons.security.webservice.AuthenticationResult;
import br.org.sesc.commons.security.webservice.SescAuthentication;
import io.redspark.ApplicationSecurity;
import io.redspark.service.PermissaoService;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SescSecurityConfigurerAdapter extends GlobalAuthenticationConfigurerAdapter {

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

	@Autowired
	private SescApplicationUser sescApplicationUser;

	/**
	 * WEB SERVICES
	 */
	@Bean
	public WebServiceMessageFactory messageFactory() {
		return new SaajSoapMessageFactory();
	}

	@Bean
	public WebServiceTemplate securityWebService() {

		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(AuthenticationMessage.class, SescAuthentication.class, AuthenticationResult.class, AuthenticationResponse.class);

		WebServiceTemplate template = new WebServiceTemplate(messageFactory());
		template.setMarshaller(marshaller);
		template.setUnmarshaller(marshaller);
		template.setDefaultUri(authenticationUrl);

		return template;
	}

	/**
	 * VALIDATORS
	 */
	@Bean
	public List<SecurityValidation> sescAuthenticationValidators() {
		HashSecurityValidation hashSecurityValidation = new HashSecurityValidation();
		hashSecurityValidation.setCodigo(codigo);

		List<SecurityValidation> list = new ArrayList<SecurityValidation>();
		list.add(hashSecurityValidation);
		return list;
	}

	/**
	 * HOOK
	 */
	@Bean
	@SuppressWarnings("rawtypes")
	@Profile(value = { DEV, QA, PRODUCAO })
	public AuthenticationHook hook(PermissaoService service) {
		return new SescAuthenticationHook(service);
	}

	/**
	 * PROVIDERS
	 */

	/**
	 * Provider de autenticação do usuário no SESC
	 */
	@Bean
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SescWebServiceAuthenticationProvider sescWebServiceAuthenticationProvider(AuthenticationHook hook) {

		SescWebServiceAuthenticationProvider authenticationProvider = new SescWebServiceAuthenticationProvider(CustomSescUser.class);
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

	/**
	 * MANAGER
	 */
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		super.init(auth);
	}

	@Autowired
	public void setAuthenticationManagerBuilder(AuthenticationManagerBuilder auth, @Qualifier("sescWebServiceAuthenticationProvider") SescWebServiceAuthenticationProvider sescWebServiceAuthenticationProvider, @Qualifier("daoAuthenticationProvider") DaoAuthenticationProvider daoAuthenticationProvider) {
		auth.authenticationProvider(sescWebServiceAuthenticationProvider);
		auth.authenticationProvider(daoAuthenticationProvider);
	}

	/**
	 * SECURITY
	 */
	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

}
