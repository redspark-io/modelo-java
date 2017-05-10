package io.redspark;

import org.sesc.permissao.sync.loader.PermissionLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.org.sesc.notificacao.client.NotificationServiceClient;
import br.org.sesc.permissao.client.PermissaoServiceClient;
import io.redspark.mock.NotificationServiceClientMock;
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
  public NotificationServiceClient notificationServiceClient() {
    return new NotificationServiceClientMock();
  }

  @Bean
  @Qualifier("xmlPermissionLoader")
  public PermissionLoader xmlPermissionLoader() {
    return new XmlPermissionLoaderMock();
  }
  
  @Bean
  public UserDetailsService userDeatailsService() {
  	return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return null;
			}
		};
  }
}
