package io.redspark.domain.vet;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Vacina.ENTITY_NAME, schema = Vacina.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Vacina.FIND_ALL, query = " SELECT v FROM Vacina v "), @NamedQuery(name = Vacina.FIND_ALL_BY_IDS, query = " SELECT v FROM Vacina v WHERE v.id IN :ids"), })
public class Vacina {

	public static final String	ENTITY_NAME			= "vacina";
	public static final String	SCHEMA_NAME			= "public";
	public static final String	FIND_ALL				= "Vacina.findAll";
	public static final String	FIND_ALL_BY_IDS	= "Vacina.findAllByIds";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(nullable = false, length = 100)
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "vacina", fetch = LAZY)
	private List<Agendamento> agendamentos = new ArrayList<>();

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
		Vacina other = (Vacina) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
