package io.redspark.domain.vet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = Veterinario.ENTITY_NAME, schema = Veterinario.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Veterinario.FIND_ALL, query = " SELECT u FROM Veterinario u "),
		@NamedQuery(name = Veterinario.FIND_BY_ID, query = " SELECT u FROM Veterinario u WHERE u.id = :id "), })
public class Veterinario {

	public static final String ENTITY_NAME = "veterinario";
	public static final String SCHEMA_NAME = "public";
	public static final String FIND_ALL = "Veterinario.findAll";
	public static final String FIND_BY_ID = "Veterinario.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@NotNull
	@Column(nullable = false, length = 100)
	private String nome;

	@OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL)
	private List<Consulta> consultas = new ArrayList<>();

	public Veterinario() {

	}

	public Veterinario(String nome) {
		super();
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
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
		Veterinario other = (Veterinario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
