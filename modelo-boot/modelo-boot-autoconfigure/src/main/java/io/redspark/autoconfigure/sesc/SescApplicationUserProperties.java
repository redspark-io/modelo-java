package io.redspark.autoconfigure.sesc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.AuthorityUtils;

import br.org.sesc.commons.security.SescUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "sesc.authentication.app")
public class SescApplicationUserProperties {
private List<String> login = new ArrayList<String>();
	
	private List<String> name = new ArrayList<String>();

	private List<String> password = new ArrayList<String>();

	private List<String> chapa = new ArrayList<String>();

	private List<String> roles = new ArrayList<String>();
	
	private List<String> sistema = new ArrayList<String>();
	
	private List<String> usuid = new ArrayList<String>();
	
	private List<String> unidade = new ArrayList<String>();
	
	private List<String> usucodigo = new ArrayList<String>();

	public SescUser getSescUser(Integer index) {
		SescUser sescUser = new SescUser();
		sescUser.setLogin(login.get(index));
		sescUser.setNome(name.get(index));
		sescUser.setPassword(password.get(index));
		sescUser.setChapa(chapa.get(index));
		sescUser.setAuthorities(AuthorityUtils
				.commaSeparatedStringToAuthorityList(roles.get(index)));
		sescUser.setSisCodigo(Long.valueOf(sistema.get(index)));
		sescUser.setUsuId(Long.valueOf(usuid.get(index)));
		sescUser.setUnidadeOrcamentaria(Long.valueOf(unidade.get(index)));
		sescUser.setUsuCodigo(Long.valueOf(usucodigo.get(index)));
		return sescUser;
	}

	public Integer size() {
		int min = Math.min(login.size(), password.size());
		min = Math.min(min, roles.size());
		return min;
	}

}
