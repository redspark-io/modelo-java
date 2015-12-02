package io.redspark.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.redspark.domain.User;
import io.redspark.repository.UserRepository;

@Component
public class UserLoginService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User u = userRepository.findByLogin(username);
    if (u == null) {
      throw new UsernameNotFoundException(username);
    }

    DefaultUser user = buildPrincipal(u);

    return user;
  }

  public DefaultUser buildPrincipal(User u) {
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
