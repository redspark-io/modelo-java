package io.redspark.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class DefaultUser implements UserDetails {

  private static final long serialVersionUID = 9145350109154466481L;

  private Long id;
  private String name;
  @JsonIgnore
  private String password;
  private String username;
  @JsonIgnore
  private List<GrantedAuthority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

}
