package io.redspark.autoconfigure.sesc.auth;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import io.redspark.SampleAppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleAppConfig.class)
public class SescPermissaoClientAutoconfigurationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void mustLoadSyncConfiguration() throws Exception {
		boolean wasLoadBean = context.containsBean("syncConfiguration");
		
		Assert.assertTrue(wasLoadBean);
	}
	
	@Test
	public void mustLoadPermissaoServiceClient() throws Exception {
		boolean wasLoadBean = context.containsBean("permissaoServiceClient");
		Assert.assertTrue(wasLoadBean);
	}
}
