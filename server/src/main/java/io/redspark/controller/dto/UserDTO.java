package io.redspark.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.authority.AuthorityUtils;

import br.org.sesc.commons.security.SescUser;

@Data
@NoArgsConstructor
public class UserDTO {

	private Long id;

	private String name;

	private Boolean admin;

	private Boolean accessChecklist;

	public UserDTO(SescUser user) {
		this.id = user.getUsuId();
		this.name = user.getUsername();
		this.admin = user.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0));
	}

}
