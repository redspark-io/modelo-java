package io.redspark.security.sesc;

import io.redspark.service.PermissaoService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.org.sesc.commons.security.AuthenticationHook;

public class SescAuthenticationHook implements AuthenticationHook<CustomSescUser> {

	private final PermissaoService permissaoServiceClient;

	public SescAuthenticationHook(PermissaoService permissaoServiceClient) {
		super();
		this.permissaoServiceClient = permissaoServiceClient;
	}

	@Override
	public void execute(CustomSescUser user) {
		for (String s : permissaoServiceClient.recuperarPermissoes(user.getSisCodigo(), user.getUsuCodigo())) {
			user.addAuthority(new SimpleGrantedAuthority(s));
		}
	}

}
