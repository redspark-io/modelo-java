package io.redspark.security.sesc;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import br.org.sesc.commons.security.SescUser;

@Component
@ConfigurationProperties(prefix = "sesc.authentication.app")
@Data
@NoArgsConstructor
public class SescApplicationUser {

	private List<String> login = new ArrayList<String>();

	private List<String> password = new ArrayList<String>();

	private List<String> roles = new ArrayList<String>();

	public SescUser getSescUser(Integer index) {
		SescUser sescUser = new SescUser();
		sescUser.setLogin(login.get(index));
		sescUser.setPassword(password.get(index));
		sescUser.setAuthorities(AuthorityUtils
		    .commaSeparatedStringToAuthorityList(roles.get(index)));
		return sescUser;
	}

	public Integer size() {
		int min = Math.min(login.size(), password.size());
		min = Math.min(min, roles.size());
		return min;
	}

}
