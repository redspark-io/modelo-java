/**
 * 
 */
package io.redspark.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author GSuaki
 *
 */
public interface PermissaoService {
	
	public Collection<String> recuperarPermissoes(long sistema, long usuario);
	
	public List<Map<String, Object>> recuperarPefis(long sistema);
	
	public List<Map<String, Object>> recuperarUsuarioPorSistemaEPerfil(long sistemaId, long perfil);
}
