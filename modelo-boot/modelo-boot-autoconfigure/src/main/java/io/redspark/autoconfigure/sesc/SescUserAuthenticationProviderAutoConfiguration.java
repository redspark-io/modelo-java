package io.redspark.autoconfigure.sesc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.commons.security.SescUser;
import br.org.sesc.commons.security.SescWebServiceAuthenticationProvider;
import br.org.sesc.commons.security.validation.HashSecurityValidation;
import br.org.sesc.commons.security.validation.SecurityValidation;
import br.org.sesc.commons.security.webservice.AuthenticationMessage;
import br.org.sesc.commons.security.webservice.AuthenticationResponse;
import br.org.sesc.commons.security.webservice.AuthenticationResult;
import br.org.sesc.commons.security.webservice.SescAuthentication;
import br.org.sesc.permissao.client.PermissaoServiceClient;

@Configuration
@ConditionalOnClass({SescWebServiceAuthenticationProvider.class})
@AutoConfigureAfter(SescPermissaoClientAutoConfiguration.class)
public class SescUserAuthenticationProviderAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public WebServiceMessageFactory messageFactory() {
		return new SaajSoapMessageFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(WebServiceMessageFactory.class)
	@ConditionalOnProperty("sesc.authentication.url")
	public WebServiceTemplate securityWebService(WebServiceMessageFactory messageFactory, @Value("${sesc.authentication.url}") String authenticationUrl) {

		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(AuthenticationMessage.class, SescAuthentication.class, AuthenticationResult.class,
		    AuthenticationResponse.class);

		WebServiceTemplate template = new WebServiceTemplate(messageFactory);
		template.setMarshaller(marshaller);
		template.setUnmarshaller(marshaller);
		template.setDefaultUri(authenticationUrl);

		return template;
	}

	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(PermissaoServiceClient.class)
	public AuthenticationHook authenticationHook(PermissaoServiceClient psc) {
		return new DefaultAutheticationHook(psc);
	}
	
	//TODO deve receber securityWebService via parametro para o link @ConditionalOnBean funcionar corretamente
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(value = AuthenticationHook.class)
	public SescWebServiceAuthenticationProvider sescUserAuthenticationProvider(final AuthenticationHook hook, List<SecurityValidation> sescAuthenticationValidators) {

		SescWebServiceAuthenticationProvider authenticationProvider = new SescWebServiceAuthenticationProvider(
				SescUser.class); 
		authenticationProvider.setValidations(sescAuthenticationValidators);
		authenticationProvider.setHook(hook);

		return authenticationProvider;
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty("sesc.authentication.app.codigo")
	public List<SecurityValidation> sescAuthenticationValidators(@Value("${sesc.authentication.app.codigo}") Long codigo) {
		HashSecurityValidation hashSecurityValidation = new HashSecurityValidation();
		hashSecurityValidation.setCodigo(codigo);

		List<SecurityValidation> list = new ArrayList<SecurityValidation>();
		list.add(hashSecurityValidation);
		return list;
	}
}
