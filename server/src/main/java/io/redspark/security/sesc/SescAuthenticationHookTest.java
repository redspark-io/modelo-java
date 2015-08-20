package io.redspark.security.sesc;

import io.redspark.security.Roles;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.org.sesc.commons.security.AuthenticationHook;
import br.org.sesc.commons.security.SescUser;

public class SescAuthenticationHookTest implements AuthenticationHook<SescUser> {

	@Value("${sesc.authentication.app.roles[0]}")
	private String roles;
	
	@Value("${sesc.authentication.app.login[0]}")
	private String login;
	
	@Value("${sesc.authentication.app.roles[1]}")
	private String roles_unidade;
	
	@Value("${sesc.authentication.app.login[1]}")
	private String login_unidade;
	
	public boolean isLoginTeste(String test) {
		
		if( login_unidade.equals(test) || login.equals(test) ){
			return true;
		}
		
		return false;
	}
	
	public List<GrantedAuthority>getRoles(String test) {
		
		String roleSelected = new String();
		
		if( login_unidade.equals(test) ) {
			roleSelected = roles_unidade;
		} else if( login.equals(test) ) {
			roleSelected = roles;
		}
		
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roleSelected);
	}
	
	@Override
	public void execute(SescUser user) {
		if( isLoginTeste(user.getLogin()) ) {
			List<GrantedAuthority> list = getRoles(user.getLogin()); 
			for (GrantedAuthority grantedAuthority : list) {
				user.addAuthority(grantedAuthority);
			}
		} else {
			user.addAuthority(new SimpleGrantedAuthority(Roles.ROLE_ADMIN));
			user.addAuthority(new SimpleGrantedAuthority(Roles.ROLE_USER));
		}
		
		
		System.out.println("HOOK TEST <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
	}

}