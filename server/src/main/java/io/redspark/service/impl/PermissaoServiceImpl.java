package io.redspark.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.org.sesc.permissao.sync.BasicAuthClienteHttpRequestFactory;
import br.org.sesc.permissao.sync.Item;
import br.org.sesc.permissao.sync.PerfilItem;
import br.org.sesc.permissao.sync.PermissaoServiceClient;
import br.org.sesc.permissao.sync.UsuarioItem;
import br.org.sesc.permissao.sync.config.SyncConfiguration;
import io.redspark.service.PermissaoService;

public class PermissaoServiceImpl extends PermissaoServiceClient implements PermissaoService {

	/**
	 * 
	 */
	private static final String USUARIO_API_URL = "/api/usuarios/sistemas/{sistema_id}/perfis/{perfil}/";

	private static final String PERFIL_API_URL = "/api/sistemas/{sistema_id}/perfis";

	private static final String PERMISSAO_API_URL = "/api/usuarios/{usuarios_id}/permissoes?sistema_id={sistema_id}&show_exceptions=true";
	
	private final SyncConfiguration config;

	public PermissaoServiceImpl(SyncConfiguration config) {
		super(config);
		this.config = config;
	}

	@Override
	public Collection<String> recuperarPermissoes(long sistema, long usuario) {
		Collection<String> result = new ArrayList<String>();
		
		try {
			RestTemplate template = new RestTemplate(new BasicAuthClienteHttpRequestFactory(config.getLogin(), config.getPassword()));
			template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			String url = config.getUrl() + PERMISSAO_API_URL;
			
			Map<String, Long> urlVariables = new HashMap<String, Long>();
			urlVariables.put("usuarios_id", usuario);
			urlVariables.put("sistema_id", sistema);
			
			Item[] permissoes = template.getForObject(url, Item[].class, urlVariables);
			for (Item p : permissoes) {
				result.add(p.getNome());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> recuperarPefis(long sistema) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			RestTemplate template = new RestTemplate(new BasicAuthClienteHttpRequestFactory(config.getLogin(), config.getPassword()));
			template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			String url = config.getUrl() + PERFIL_API_URL;
			
			Map<String, Long> urlVariables = new HashMap<String, Long>();
			urlVariables.put("sistema_id", sistema);
			
			PerfilItem[] permissoes = template.getForObject(url, PerfilItem[].class, urlVariables);
			for (PerfilItem p : permissoes) {
				Map<String, Object> usuMap = new HashMap<String, Object>();
				usuMap.put("id", p.getId());
				usuMap.put("nome", p.getNome());
				result.add(usuMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> recuperarUsuarioPorSistemaEPerfil(long sistemaId, long perfil) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			RestTemplate template = new RestTemplate(new BasicAuthClienteHttpRequestFactory(config.getLogin(), config.getPassword()));
			template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			String url = config.getUrl() + USUARIO_API_URL;
			
			Map<String, Object> urlVariables = new HashMap<String, Object>();
			urlVariables.put("sistema_id", sistemaId);
			urlVariables.put("perfil", perfil);
			
			UsuarioItem[] usuarios = template.getForObject(url, UsuarioItem[].class, urlVariables);
			for (UsuarioItem usuarioItem : usuarios) {
				Map<String, Object> usuMap = new HashMap<String, Object>();
				usuMap.put("usuId", usuarioItem.getUsuId());
				usuMap.put("uo", usuarioItem.getUo());
				result.add(usuMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
