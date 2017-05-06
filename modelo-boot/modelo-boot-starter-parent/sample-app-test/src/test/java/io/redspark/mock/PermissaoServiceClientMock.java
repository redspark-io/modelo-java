package io.redspark.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.sesc.permissao.client.Item;
import br.org.sesc.permissao.client.PerfilItem;
import br.org.sesc.permissao.client.PermissaoServiceClient;
import br.org.sesc.permissao.client.UsuarioItem;

public class PermissaoServiceClientMock extends PermissaoServiceClient {

	public PermissaoServiceClientMock() {
		super(null);
	}

	@Override
	public List<PerfilItem> recuperarPefis(long sistema, long unidade) {
		return new ArrayList<>();
	}

	@Override
	public List<PerfilItem> recuperarPefis(long sistema) {
		return new ArrayList<>();
	}

	@Override
	public Collection<String> recuperarPermissoes(long arg0, long arg1, long arg2) {
		return new ArrayList<>();
	}

	@Override
	public List<Item> recuperarPermissoesPorSistemaEPefil(long arg0, long arg1) {
		return new ArrayList<>();
	}

	@Override
	public List<UsuarioItem> recuperarUsuarioPorSistema(long arg0) {
		return new ArrayList<>();
	}

	@Override
	public List<UsuarioItem> recuperarUsuarioPorSistemaEPerfil(long arg0, long arg1) {
		return new ArrayList<>();
	}

	@Override
	public List<UsuarioItem> recuperarUsuarioPorSistemaEPerfilEUnidade(long arg0, long arg1, long arg2) {
		return new ArrayList<>();
	}

	@Override
	public List<UsuarioItem> recuperarUsuarioPorSistemaEPermissao(long arg0, String arg1) {
		return new ArrayList<>();
	}

	@Override
	public List<UsuarioItem> recuperarUsuarioPorSistemaEUnidade(long arg0, long arg1) {
		return new ArrayList<>();
	}
}
