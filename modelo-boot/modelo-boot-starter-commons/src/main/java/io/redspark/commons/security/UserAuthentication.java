package io.redspark.commons.security;

public interface UserAuthentication {

  String getLogin();

  String getPassword();
  
  Long getId();
}
