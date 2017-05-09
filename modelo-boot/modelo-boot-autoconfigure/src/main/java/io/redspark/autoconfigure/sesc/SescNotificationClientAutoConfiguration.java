package io.redspark.autoconfigure.sesc;

import static io.redspark.autoconfigure.constants.ApplicationProfile.HOMOLOG;
import static io.redspark.autoconfigure.constants.ApplicationProfile.PRODUCAO;
import static io.redspark.autoconfigure.constants.ApplicationProfile.QA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.org.sesc.notificacao.client.NotificationServiceClient;
import br.org.sesc.notificacao.client.config.DefaultNotificationConfiguration;
import br.org.sesc.notificacao.client.config.NotificationConfiguration;
import br.org.sesc.notificacao.client.service.impl.NotificationServiceClientImpl;

@Configuration
@Profile({HOMOLOG, PRODUCAO, QA})
@ConditionalOnClass(NotificationServiceClient.class)
public class SescNotificationClientAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "sesc.notificacao", name = { "url", "login", "password" })
  public NotificationConfiguration notificationConfiguration(@Value("${sesc.notificacao.url}") String url,
      @Value("${sesc.notificacao.login}") String login, @Value("${sesc.notificacao.password}") String password) {

    return DefaultNotificationConfiguration.builder()
        .url(url)
        .login(login)
        .password(password)
        .build();
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(NotificationConfiguration.class)
  public NotificationServiceClient notificationServiceClient(final NotificationConfiguration configuration) {
    return new NotificationServiceClientImpl(configuration);
  }
}
