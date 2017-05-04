package io.redspark.autoconfigure.sesc.auth;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.commons.security.SescUser;
import br.org.sesc.permissao.client.PermissaoServiceClient;

public class DefaultAutheticationHook implements AuthenticationHook {
	private PermissaoServiceClient client;
	
	public DefaultAutheticationHook(PermissaoServiceClient client) {
		this.client = client;
	}

	@Override
	public void execute(SescUser sescUser) {

	}
}
