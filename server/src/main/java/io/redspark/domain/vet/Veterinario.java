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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.NonNull;

@Entity
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })	
@Table(name = Veterinario.ENTITY_NAME, schema = Veterinario.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Veterinario.FIND_ALL, query = " SELECT u FROM Veterinario u "), @NamedQuery(name = Veterinario.FIND_BY_ID, query = " SELECT u FROM Veterinario u WHERE u.id = :id "), })
public class Veterinario implements Serializable {

	private static final long serialVersionUID = 7468567096349207353L;

	public static final String	ENTITY_NAME	= "veterinario";
	public static final String	SCHEMA_NAME	= "public";
	public static final String	FIND_ALL		= "Veterinario.findAll";
	public static final String	FIND_BY_ID	= "Veterinario.findById";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NotNull
	@NonNull
	@Column(nullable = false, length = 100)
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "veterinario", fetch = LAZY)
	private List<Consulta> consultas = new ArrayList<>();

	public Veterinario() {
		super();
	}

	public Veterinario(Long id, String nome, List<Consulta> consultas) {
		super();
		this.id = id;
		this.nome = nome;
		this.consultas = consultas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
