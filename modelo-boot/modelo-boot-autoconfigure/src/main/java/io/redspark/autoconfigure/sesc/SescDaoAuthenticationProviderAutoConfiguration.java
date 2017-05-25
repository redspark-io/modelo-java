package io.redspark.autoconfigure.sesc;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.org.sesc.commons.security.SescUser;
import br.org.sesc.commons.security.test.SimpleUserDetailService;

@Configuration
@ConditionalOnClass(value = DaoAuthenticationProvider.class)
@EnableConfigurationProperties(SescApplicationUserProperties.class)
@ConditionalOnProperty(prefix = "sesc.authentication.security.basic", name = "enabled", havingValue = "true")
public class SescDaoAuthenticationProviderAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public UserDetailsService userDetailsService(SescApplicationUserProperties sescApplicationUser) {

    ArrayList<SescUser> userAuthList = new ArrayList<SescUser>();
    for (int i = 0; i < sescApplicationUser.size(); i++) {
      userAuthList.add(sescApplicationUser.getSescUser(i));
    }

    return new SimpleUserDetailService(userAuthList);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(value = UserDetailsService.class)
  public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setUserDetailsService(userDetailsService);

    return authenticationProvider;
  }

}
