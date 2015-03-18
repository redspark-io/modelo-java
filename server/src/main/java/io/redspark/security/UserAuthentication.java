package io.redspark.security;

public interface UserAuthentication {

    Long getId();

    String getLogin();

    String getPassword();
}
