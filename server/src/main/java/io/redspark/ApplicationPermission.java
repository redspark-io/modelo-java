/**
 * 
 */
package io.redspark;

import static io.redspark.AppProfile.PRODUCAO;
import static io.redspark.AppProfile.QA;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;

import br.org.sesc.permissao.sync.SyncService;
import br.org.sesc.permissao.sync.annotation.AnnotatedControllerPermissionLoader;
import br.org.sesc.permissao.sync.config.DefaultSyncConfiguration;
import br.org.sesc.permissao.sync.xml.XmlPermissionLoader;
import io.redspark.service.PermissaoService;
import io.redspark.service.impl.PermissaoServiceImpl;

/**
 * @author GSuaki
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationPermission {
	
	/**
	 * PERMISSÃO PROPERTIES
	 */
	@Value("${sesc.administrativo.permissao-url}")
	private String permissaoUrl;

	@Value("${sesc.administrativo.permissao-hash}")
	private String permissaoHash;

	@Value("${sesc.administrativo.permissao-login}")
	private String permissaoLogin;

	@Value("${sesc.administrativo.permissao-password}")
	private String permissaoPassword;

	/** 
	 *  PERMISSOES
	 */
	@Bean
	public DefaultSyncConfiguration defaultSyncConfiguration() {
		DefaultSyncConfiguration sync = new DefaultSyncConfiguration();
		sync.setHash(permissaoHash);
		sync.setLogin(permissaoLogin);
		sync.setPassword(permissaoPassword);
		sync.setUrl(permissaoUrl);
		return sync;
	}

	@Bean
	@Profile(value = { QA, PRODUCAO })
	public AnnotatedControllerPermissionLoader annotationPermissionLoader() {
		AnnotatedControllerPermissionLoader acpl = new AnnotatedControllerPermissionLoader();
		return acpl;
	}

	@Bean
	@Profile(value = { QA, PRODUCAO })
	public XmlPermissionLoader xmlPermissionLoader() throws IOException, URISyntaxException {
		XmlPermissionLoader xml = new XmlPermissionLoader();

		URL url = this.getClass().getResource("/permissoes.xml");
		FileSystemResource fsr = new FileSystemResource(new File(url.toURI()));

		xml.setFile(fsr);
		return xml;
	}

	@Bean
	@Profile(value = { QA, PRODUCAO })
	public SyncService syncService(DefaultSyncConfiguration dsc, AnnotatedControllerPermissionLoader acpl, XmlPermissionLoader xpl) {
		SyncService syncService = new SyncService(dsc);
		syncService.setLoaders(Arrays.asList(acpl, xpl));
		return syncService;
	}

	@Bean
	public PermissaoService permissaoServiceClient(DefaultSyncConfiguration dsc) {
		return new PermissaoServiceImpl(dsc);
	}
}
