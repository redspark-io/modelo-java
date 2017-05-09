package io.redspark.autoconfigure.sesc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import io.redspark.ApplicationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
public class SescSecurityAutoConfigurationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void mostLoadSecurityContext() throws Exception {
	}
}