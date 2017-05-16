package io.redspark.commons.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {

  public static UserDetails getUserLogged() {
    return (UserDetails) getLoggedAsPrincipal();
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

  public static boolean isUserLoggedAsHolmesUser(UserDetails defaultUser) {
    return defaultUser != null && UserDetails.class.isAssignableFrom(defaultUser.getClass());
  }

}
