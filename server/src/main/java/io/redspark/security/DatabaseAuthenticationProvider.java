package io.redspark.security;

import io.redspark.domain.User;
import io.redspark.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
	    UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
	    throws AuthenticationException {
	User u = userRepository.findByLogin(username);
	if (u == null) {
	    throw new UsernameNotFoundException(username);
	}
	
	DefaultUser user = new DefaultUser();
	user.setId(u.getId());
	user.setName(u.getName());
	user.setUsername(u.getLogin());
	user.setPassword(u.getPassword());
	user.setAccountNonExpired(true);
	user.setAccountNonLocked(true);
	user.setCredentialsNonExpired(true);
	user.setEnabled(true);
	if (u.getAdmin()) {
	    user.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
	} else {
	    user.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));
	}
	
	return user;
    }

}
