package io.redspark.security;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import br.org.sesc.commons.security.SescUser;

public class UserUtils {

	public static SescUser getUserLogged() {
		return (SescUser) getLoggedAsPrincipal();
	}

	private static Object getLoggedAsPrincipal() {
		if (getContext() == null) {
			return null;
		}
		
		if (getContext().getAuthentication() == null) {
			return null;
		}
		
		return getContext().getAuthentication().getPrincipal();
	}

	public static boolean isUserLoggedAsSescUser() {
		return getLoggedAsPrincipal() != null && SescUser.class.isAssignableFrom(getLoggedAsPrincipal().getClass());
	}

}
