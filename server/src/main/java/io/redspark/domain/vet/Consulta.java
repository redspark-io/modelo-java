package io.redspark.domain.vet;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Entity
@Builder
@Table(name = Consulta.ENTITY_NAME, schema = Consulta.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Consulta.FIND_ALL, query = " SELECT u FROM Consulta u "), @NamedQuery(name = Consulta.FIND_BY_ID, query = " SELECT u FROM Consulta u WHERE u.id = :id "), })
public class Consulta {

	public static final String	ENTITY_NAME	= "consulta";
	public static final String	SCHEMA_NAME	= "public";
	public static final String	FIND_ALL		= "Consulta.findAll";
	public static final String	FIND_BY_ID	= "Consulta.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_veterinario")
	private Veterinario veterinario;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_animal")
	private Animal animal;

	@Column(name = "data_consulta", nullable = false)
	private Date dateConsulta;

	@JsonIgnore
	@OneToMany(mappedBy = "consulta", fetch = LAZY)
	private List<Agendamento> agendamentos = new ArrayList<>();

	public Consulta() {
		super();
	}

	public Consulta(Veterinario veterinario, Animal animal, Date dateConsulta) {
		super();
		this.veterinario = veterinario;
		this.animal = animal;
		this.dateConsulta = dateConsulta;
	}

	public Consulta(Long id, Veterinario veterinario, Animal animal, Date dateConsulta) {
		super();
		this.id = id;
		this.veterinario = veterinario;
		this.animal = animal;
		this.dateConsulta = dateConsulta;
	}

	public Consulta(Veterinario veterinario, Animal animal, Date dateConsulta, List<Agendamento> agendamentos) {
		super();
		this.veterinario = veterinario;
		this.animal = animal;
		this.dateConsulta = dateConsulta;
		this.agendamentos = agendamentos;
	}

	public Consulta(Long id, Veterinario veterinario, Animal animal, Date dateConsulta, List<Agendamento> agendamentos) {
		super();
		this.id = id;
		this.veterinario = veterinario;
		this.animal = animal;
		this.dateConsulta = dateConsulta;
		this.agendamentos = agendamentos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Date getDateConsulta() {
		return dateConsulta;
	}

	public void setDateConsulta(Date dateConsulta) {
		this.dateConsulta = dateConsulta;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
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
		Consulta other = (Consulta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
