package io.redspark.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.sesc.permissao.sync.config.SyncConfiguration;

public class PermissaoServiceTest extends PermissaoService {
	
	public PermissaoServiceTest(SyncConfiguration config) {
		super(config);
	}

	@Override
	public Collection<String> recuperarPermissoes(long sistema, long ususario) {
		Collection<String> result = new ArrayList<String>();
		return result;
	}
	
	@Override
	public List<Map<String, Object>> recuperarUsuarioPorSistemaEPermissao(long sistemaId, String permissao) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uo", new Integer(10));
		map.put("usuId", new Integer(100));
		
		result.add(map);
		
		return result;
	}
}