package io.redspark.domain.vet;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Dono.ENTITY_NAME, schema = Dono.SCHEMA_NAME)
public class Dono implements Serializable {

	private static final long serialVersionUID = -7380415448649459773L;

	public static final String ENTITY_NAME = "dono";
	public static final String SCHEMA_NAME = "public";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NonNull
	@NotNull
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "email", length = 100)
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "dono", fetch = LAZY)
	private List<Animal> animais = new ArrayList<Animal>();

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
