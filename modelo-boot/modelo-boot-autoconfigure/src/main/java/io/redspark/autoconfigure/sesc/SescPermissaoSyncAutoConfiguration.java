package io.redspark.autoconfigure.sesc;

import java.util.Arrays;

import org.sesc.permissao.sync.listener.SyncApplicationListener;
import org.sesc.permissao.sync.loader.PermissionLoader;
import org.sesc.permissao.sync.loader.impl.AnnotatedControllerPermissionLoader;
import org.sesc.permissao.sync.loader.impl.XmlPermissionLoader;
import org.sesc.permissao.sync.service.SyncService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import br.org.sesc.permissao.client.config.SyncConfiguration;

@Configuration
@ConditionalOnClass(PermissionLoader.class)
public class SescPermissaoSyncAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public PermissionLoader annotatedControllerPermissionLoader() {
		return new AnnotatedControllerPermissionLoader();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty("sesc.permissao.file.path")
	public PermissionLoader xmPermissionLoader(@Value("${sesc.permissao.file.path}") String file) {
		XmlPermissionLoader xmlPermissionLoader = new XmlPermissionLoader();
		Resource classPathResource = new ClassPathResource(file);
		xmlPermissionLoader.setFile(classPathResource);
		
		return xmlPermissionLoader;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean({SyncConfiguration.class, XmlPermissionLoader.class, AnnotatedControllerPermissionLoader.class})
	public SyncService syncService(final SyncConfiguration syncConfiguration,
	    @Qualifier("xmPermissionLoader") final PermissionLoader xmPermissionLoader,
	    @Qualifier("annotatedControllerPermissionLoader") final PermissionLoader annotatedControllerPermissionLoader) {

		SyncService syncService = new SyncService(syncConfiguration);
		syncService.setLoaders(Arrays.asList(xmPermissionLoader, annotatedControllerPermissionLoader));

		return syncService;
	}

	@Bean
	@ConditionalOnBean(SyncService.class)
	@ConditionalOnMissingBean
	public ApplicationListener<?> syncApplicationListener(final SyncService syncService) {
		return new SyncApplicationListener(syncService);
	}
}
