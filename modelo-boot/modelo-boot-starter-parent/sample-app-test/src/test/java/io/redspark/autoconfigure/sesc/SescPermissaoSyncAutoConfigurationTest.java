package io.redspark.autoconfigure.sesc;

import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.sesc.permissao.sync.loader.PermissionLoader;
import org.sesc.permissao.sync.loader.impl.AnnotatedControllerPermissionLoader;
import org.sesc.permissao.sync.loader.impl.XmlPermissionLoader;
import org.sesc.permissao.sync.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import io.redspark.ApplicationTest;
import io.redspark.mock.XmlPermissionLoaderMock;

public class SescPermissaoSyncAutoConfigurationTest extends ApplicationTest {
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void mustLoadAnnotatedControllerPermissionLoader() throws Exception {
		boolean wasLoadBean = context.containsBean("annotatedControllerPermissionLoader");
		Assert.assertTrue(wasLoadBean);
		
		AnnotatedControllerPermissionLoader bean = (AnnotatedControllerPermissionLoader) context.getBean("annotatedControllerPermissionLoader");
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadXmlPermissionLoader() throws Exception {
		boolean wasLoadBean = context.containsBean("xmlPermissionLoader");
		Assert.assertTrue(wasLoadBean);
		
		XmlPermissionLoaderMock bean = (XmlPermissionLoaderMock) context.getBean("xmlPermissionLoader");
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadSyncService() throws Exception {
		boolean wasLoadBean = context.containsBean("syncService");
		Assert.assertTrue(wasLoadBean);
		
		SyncService bean = context.getBean(SyncService.class);
		Assert.assertNotNull(bean);
		
		List<PermissionLoader> loaders = bean.getLoaders();
		Assert.assertThat(loaders, Matchers.hasSize(2));
		Assert.assertNotNull(loaders.get(0));
		Assert.assertNotNull(loaders.get(1));
	}
}