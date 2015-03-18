package io.redspark.security;

import org.springframework.security.core.context.SecurityContextHolder;

import br.org.sesc.commons.security.SescUser;

public class UserUtils {

	public static SescUser getUserLogged() {
		return (SescUser) getLoggedAsPrincipal();
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

	public static boolean isUserLoggedAsHolmesUser() {
		return getLoggedAsPrincipal() != null && SescUser.class.isAssignableFrom(getLoggedAsPrincipal().getClass());
	}

}
