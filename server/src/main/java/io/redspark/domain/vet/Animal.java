package io.redspark.domain.vet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = Animal.ENTITY_NAME, schema = Animal.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Animal.FIND_ALL, query = " SELECT u FROM Animal u "), })
public class Animal {

	public static final String ENTITY_NAME = "animal";
	public static final String SCHEMA_NAME = "public";
	public static final String FIND_ALL = "Animal.findAll";
	public static final String FIND_BY_ID = "Animal.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NotNull
	@Column(nullable = false, length = 100)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_dono", nullable = false)
	private Dono dono;

	@JsonIgnore
	@OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Agendamento> agendamentos = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Consulta> consultas = new ArrayList<>();

	public Animal() {

	}

	public Animal(String nome) {
		super();
		this.nome = nome;
	}

	public Animal(String nome, Dono dono) {
		super();
		this.nome = nome;
		this.dono = dono;
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
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
