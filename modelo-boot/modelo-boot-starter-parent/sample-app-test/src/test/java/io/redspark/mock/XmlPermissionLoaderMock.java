package io.redspark.mock;

import java.util.HashSet;
import java.util.Set;

import org.sesc.permissao.sync.loader.PermissionLoader;

import br.org.sesc.permissao.client.PermissaoItem;

public class XmlPermissionLoaderMock implements PermissionLoader {

	@Override
	public Set<PermissaoItem> load() {
		// TODO Auto-generated method stub
		return new HashSet<>();
	}
}
