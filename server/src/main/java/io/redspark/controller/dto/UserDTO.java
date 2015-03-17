package io.redspark.controller.dto;

import io.redspark.security.DefaultUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.authority.AuthorityUtils;

@Data
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    
    private String name;
    
    private Boolean admin;
    
    private Boolean accessChecklist;
    
    public UserDTO(DefaultUser user) {
	this.id = user.getId();
	this.name = user.getName();
	this.admin = user.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0));
    }

}
