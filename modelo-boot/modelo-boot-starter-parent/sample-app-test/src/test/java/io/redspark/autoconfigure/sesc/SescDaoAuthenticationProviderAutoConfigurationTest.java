package io.redspark.autoconfigure.sesc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import io.redspark.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SescDaoAuthenticationProviderAutoConfigurationTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void mustLoadDaoAuthenticationProvider() throws Exception {
		boolean wasLoadProviderBean = context.containsBean("daoAuthenticationProvider");
		
		Assert.assertTrue(wasLoadProviderBean);
		
		DaoAuthenticationProvider beanProvider = context.getBean(DaoAuthenticationProvider.class);
		UserDetailsService detailsService = (UserDetailsService) ReflectionTestUtils.getField(beanProvider, "userDetailsService");
		
		Assert.assertNotNull(detailsService);
	}
}
