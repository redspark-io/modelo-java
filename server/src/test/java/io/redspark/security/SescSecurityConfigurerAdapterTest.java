/**
 * 
 */
package io.redspark.security;

import static io.redspark.AppProfile.TEST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.permissao.sync.config.DefaultSyncConfiguration;
import io.redspark.service.PermissaoService;
import io.redspark.service.PermissaoServiceTest;

/**
 * @author GSuaki
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SescSecurityConfigurerAdapterTest {
	
	/******************************************
	 ********* Servicos para test **********
	 *****************************************/

	@Bean
	@Profile({ TEST })
	public PermissaoService permissaoServiceClientTest(DefaultSyncConfiguration dsc) {
		return new PermissaoServiceTest(dsc);
	}

	@Bean
	@Profile({ TEST  })
	@SuppressWarnings("rawtypes")
	public AuthenticationHook hookTest() {
		return new SescAuthenticationHookTest();
	}

}
