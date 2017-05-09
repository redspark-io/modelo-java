package io.redspark.autoconfigure.sesc;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import br.org.sesc.notificacao.client.config.NotificationConfiguration;
import io.redspark.ApplicationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
public class SescNotificationClientAutoConfigurationTest {

  @Autowired
  private ApplicationContext context;

  @Test
  public void mustLoadNotificationConfiguration() throws Exception {

    boolean wasLoadBean = context.containsBean("notificationConfiguration");

    Assert.assertTrue("Should contains notificationConfiguration Bean", wasLoadBean);
  }

  @Test
  public void mustLoadNotificationServiceClient() throws Exception {

    boolean wasLoadBean = context.containsBean("notificationServiceClient");

    Assert.assertTrue("Should contains notificationServiceClient Bean", wasLoadBean);
  }
  
  @Test
  public void shouldLoadProperties() throws Exception {

    NotificationConfiguration configuration = context.getBean(NotificationConfiguration.class);
    
    Assert.assertThat("'sesc.notificacao.url' is not equals", configuration.getUrl(), equalTo("http://test/permissao"));
    Assert.assertThat("'sesc.notificacao.login' is not equals", configuration.getLogin(), equalTo("sesc"));
    Assert.assertThat("'sesc.notificacao.password' is not equals", configuration.getPassword(), equalTo("sesc"));
  }
}
