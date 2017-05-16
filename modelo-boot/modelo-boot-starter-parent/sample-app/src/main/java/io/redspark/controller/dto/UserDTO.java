package io.redspark.controller.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import br.org.sesc.commons.security.SescUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

  private Long id;
  private String name;
  private Collection<String> authorities;

  public UserDTO(SescUser user) {
    this.id = user.getUsuCodigo();
    this.name = user.getNome();
    this.authorities = user.getAuthorities().stream().map(g -> g.getAuthority()).collect(Collectors.toList());
  }
}