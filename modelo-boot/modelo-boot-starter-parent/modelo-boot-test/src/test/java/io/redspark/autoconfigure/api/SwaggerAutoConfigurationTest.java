package io.redspark.autoconfigure.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import io.redspark.SampleApplication;
import springfox.documentation.spring.web.plugins.Docket;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class SwaggerAutoConfigurationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void mustLoadSwagger() throws Exception {
		boolean wasLoadBean = context.containsBean("apiDefault");
		Assert.assertTrue(wasLoadBean);
		
		Docket laodBean = context.getBean(Docket.class);
		Assert.assertNotNull(laodBean);
	}
}
