package io.redspark.autoconfigure.sesc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.commons.security.SescWebServiceAuthenticationProvider;
import io.redspark.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SescUserAuthenticationProviderAutoConfigurationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void loadWsTemplate() throws Exception {
		boolean wasLoadBean = context.containsBean("securityWebService");
		Assert.assertTrue(wasLoadBean);
		
		WebServiceTemplate wsBean = (WebServiceTemplate) context.getBean(WebServiceTemplate.class);
		Assert.assertNotNull(wsBean);
	}
	
	@Test
	public void loadAuthenticationHook() throws Exception {
		boolean wasLoadBean = context.containsBean("authenticationHook");
		Assert.assertTrue(wasLoadBean);
		
		AuthenticationHook hookBean = (AuthenticationHook) context.getBean(AuthenticationHook.class);
		Assert.assertNotNull(hookBean);
	}
	
	@Test
	public void loadSescAuthenticationValidators() throws Exception {
		boolean wasLoadBean = context.containsBean("sescAuthenticationValidators");
		Assert.assertTrue(wasLoadBean);
	}
	
	@Test
	public void mustLoadSescUserAuthenticationProvider() throws Exception {
		boolean wasLoadBean = context.containsBean("sescUserAuthenticationProvider");
		Assert.assertTrue(wasLoadBean);
		
		SescWebServiceAuthenticationProvider hookBean = (SescWebServiceAuthenticationProvider) context.getBean(SescWebServiceAuthenticationProvider.class);
		Assert.assertNotNull(hookBean);
	}
}
