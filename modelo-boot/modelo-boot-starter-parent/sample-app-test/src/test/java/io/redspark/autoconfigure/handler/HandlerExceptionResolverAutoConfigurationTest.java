package io.redspark.autoconfigure.handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.redspark.ApplicationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
public class HandlerExceptionResolverAutoConfigurationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void mustLoadHandlerExceptionResolver() throws Exception {
		boolean wasLoadBean = context.containsBean("handlerExceptionResolver");
		Assert.assertTrue(wasLoadBean);
		
		HandlerExceptionResolver bean = (HandlerExceptionResolver) context.getBean("handlerExceptionResolver");
		Assert.assertNotNull(bean);
	}
}
