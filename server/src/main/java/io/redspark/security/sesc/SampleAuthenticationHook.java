package io.redspark.security.sesc;

import io.redspark.repository.UserRepository;
import io.redspark.security.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.org.sesc.commons.security.AuthenticationHook;

/**
 * Essa classe server para adicionar ao usuário autenticado mais informações, nesse exemplo estamos
 *
 */
public class SampleAuthenticationHook implements AuthenticationHook<CustomSescUser> {

    @Autowired
    private UserRepository userRepository;
    
    /**
     * Exemplo de Hook, ele procura na base local o usuário e se encontra ele cria ele com as Roles esperadas.
     */
    @Override
    public void execute(CustomSescUser user) {
	user.addAuthority(new SimpleGrantedAuthority(Roles.ROLE_ADMIN));
	user.addAuthority(new SimpleGrantedAuthority(Roles.ROLE_USER));
    }

}
