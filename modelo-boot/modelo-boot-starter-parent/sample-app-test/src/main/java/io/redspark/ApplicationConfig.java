package io.redspark;

import org.sesc.permissao.sync.loader.PermissionLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.org.sesc.notificacao.client.NotificationServiceClient;
import br.org.sesc.permissao.client.PermissaoServiceClient;
import io.redspark.mock.NotificationServiceClientMock;
import io.redspark.mock.PermissaoServiceClientMock;
import io.redspark.mock.XmlPermissionLoaderMock;

@SpringBootApplication
public class ApplicationConfig {

  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder(ApplicationConfig.class).run(args);
  }
}