package io.redspark.domain.vet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = Dono.ENTITY_NAME, schema = Dono.SCHEMA_NAME)
public class Dono implements Serializable {

  private static final long serialVersionUID = -7380415448649459773L;

  public static final String ENTITY_NAME = "dono";
  public static final String SCHEMA_NAME = "public";
  public static final String FIND_ALL    = "Dono.findAll";
  public static final String FIND_BY_ID  = "Dono.findById";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id;

  @NotNull
  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "email", length = 100)
  private String email;

  @JsonIgnore
  @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Animal> animais = new ArrayList<Animal>();

  public Dono() {

  }

  public Dono(String nome, String email) {
    super();
    this.nome = nome;
    this.email = email;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Dono other = (Dono) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
