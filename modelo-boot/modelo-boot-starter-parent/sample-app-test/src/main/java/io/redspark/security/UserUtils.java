package io.redspark.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

  public static DefaultUser getUserLogged() {
    return (DefaultUser) getLoggedAsPrincipal();
  }

  private static Object getLoggedAsPrincipal() {
    if (SecurityContextHolder.getContext() == null) {
      return null;
    }
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      return null;
    }
    return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public static boolean isUserLoggedAsHolmesUser(DefaultUser defaultUser) {
    return defaultUser != null && DefaultUser.class.isAssignableFrom(defaultUser.getClass());
  }

}
