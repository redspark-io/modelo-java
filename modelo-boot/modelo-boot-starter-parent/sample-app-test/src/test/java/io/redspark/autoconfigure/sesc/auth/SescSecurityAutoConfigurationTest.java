package io.redspark.autoconfigure.sesc.auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.redspark.SampleAppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleAppConfig.class)
public class SescSecurityAutoConfigurationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void loadSecurityContext() throws Exception {
		boolean wasLoadBean = context.containsBean("applicationWebSecurityConfigurerAdapter");
		
		Assert.assertTrue(wasLoadBean);
	}
}
