/**
 * 
 */
package io.redspark;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import br.org.sesc.administrativo.client.MatriculaClientService;
import br.org.sesc.administrativo.client.UnidadeOrcamentariaClientService;
import br.org.sesc.administrativo.client.UsuarioClientService;
import br.org.sesc.administrativo.client.rest.security.AuthenticatedRestTemplate;
import br.org.sesc.administrativo.client.service.impl.MatriculaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.UnidadeOrcamentariaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.UsuarioClientServiceImpl;

/**
 * @author GSuaki
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationBeans {

	@Value("${sesc.administrativo.user}")
	private String user;

	@Value("${sesc.administrativo.password}")
	private String password;

	@Value("${sesc.administrativo.host}")
	private String host;
	
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
}
