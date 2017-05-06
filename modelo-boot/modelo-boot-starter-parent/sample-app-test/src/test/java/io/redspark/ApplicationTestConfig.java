package io.redspark;

import org.sesc.permissao.sync.loader.PermissionLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.org.sesc.permissao.client.PermissaoServiceClient;
import io.redspark.mock.PermissaoServiceClientMock;
import io.redspark.mock.XmlPermissionLoaderMock;

@Configuration
@EnableAutoConfiguration
public class ApplicationTestConfig extends ApplicationConfig {
	@Bean
	public PermissaoServiceClient permissaoServiceClient() {
		return new PermissaoServiceClientMock();
	}
	
	@Bean
	@Qualifier("xmlPermissionLoader")
	public PermissionLoader xmlPermissionLoader() {
		return new XmlPermissionLoaderMock();
	}
}
