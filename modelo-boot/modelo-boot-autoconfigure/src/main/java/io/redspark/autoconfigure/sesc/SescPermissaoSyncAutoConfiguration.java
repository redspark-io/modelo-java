package io.redspark.autoconfigure.sesc;

import static io.redspark.autoconfigure.constants.ApplicationProfile.HOMOLOG;
import static io.redspark.autoconfigure.constants.ApplicationProfile.PRODUCAO;
import static io.redspark.autoconfigure.constants.ApplicationProfile.QA;

import java.util.Arrays;

import org.sesc.permissao.sync.listener.SyncApplicationListener;
import org.sesc.permissao.sync.loader.PermissionLoader;
import org.sesc.permissao.sync.loader.impl.AnnotatedControllerPermissionLoader;
import org.sesc.permissao.sync.loader.impl.XmlPermissionLoader;
import org.sesc.permissao.sync.service.SyncService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import br.org.sesc.permissao.client.PermissaoServiceClient;
import br.org.sesc.permissao.client.config.SyncConfiguration;

@Configuration
@ConditionalOnClass({PermissionLoader.class, PermissaoServiceClient.class})
@AutoConfigureAfter(SescPermissaoClientAutoConfiguration.class)
@Profile({HOMOLOG, PRODUCAO, QA})
public class SescPermissaoSyncAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean(name = "annotatedControllerPermissionLoader")
	@Qualifier("annotatedControllerPermissionLoader")
	public PermissionLoader annotatedControllerPermissionLoader() {
		return new AnnotatedControllerPermissionLoader();
	}

	@Bean
	@ConditionalOnMissingBean(name = "xmlPermissionLoader")
	@ConditionalOnProperty("sesc.permissao.file.path")
	@Qualifier("xmlPermissionLoader")
	public PermissionLoader xmlPermissionLoader(@Value("${sesc.permissao.file.path}") String file) {
		XmlPermissionLoader xmlPermissionLoader = new XmlPermissionLoader();
		Resource classPathResource = new ClassPathResource(file);
		xmlPermissionLoader.setFile(classPathResource);
		
		return xmlPermissionLoader;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(value = {SyncConfiguration.class}, name = {"xmlPermissionLoader", "annotatedControllerPermissionLoader"})
	public SyncService syncService(
			final SyncConfiguration syncConfiguration,
	    @Qualifier("xmlPermissionLoader") final PermissionLoader xmPermissionLoader,
	    @Qualifier("annotatedControllerPermissionLoader") final PermissionLoader annotatedControllerPermissionLoader) {

		SyncService syncService = new SyncService(syncConfiguration);
		syncService.setLoaders(Arrays.asList(xmPermissionLoader, annotatedControllerPermissionLoader));

		return syncService;
	}

	@Bean
	@ConditionalOnBean(SyncService.class)
	@ConditionalOnMissingBean
	public SyncApplicationListener syncApplicationListener(final SyncService syncService) {
		return new SyncApplicationListener(syncService);
	}
}
