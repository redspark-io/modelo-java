package io.redspark.autoconfigure.sesc.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.org.sesc.permissao.client.PermissaoServiceClient;
import br.org.sesc.permissao.client.config.DefaultSyncConfiguration;
import br.org.sesc.permissao.client.config.SyncConfiguration;

@Configuration
//@ConditionalOnClass(PermissaoServiceClient.class) //FIXME entender porque não carrega 
public class SescPermissaoClientAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "sesc.permissao", name = {"url", "login", "password", "hash"})
	public SyncConfiguration syncConfiguration(@Value("${sesc.permissao.url}") String url,
	    @Value("${sesc.permissao.login}") String login, @Value("${sesc.permissao.password}") String password,
	    @Value("${sesc.permissao.hash}") String hash) {
		DefaultSyncConfiguration defaultSyncConfiguration = new DefaultSyncConfiguration();
		defaultSyncConfiguration.setUrl(url);
		defaultSyncConfiguration.setPassword(password);
		defaultSyncConfiguration.setHash(hash);
		defaultSyncConfiguration.setLogin(login);

		return defaultSyncConfiguration;
	}
	
	@Bean
	@Profile({ "DEV", "PRODUCAO" })
	@ConditionalOnMissingBean
	@ConditionalOnBean(SyncConfiguration.class)
	public PermissaoServiceClient permissaoServiceClient(SyncConfiguration syncConfiguration) {
		return new PermissaoServiceClient(syncConfiguration);
	}
}