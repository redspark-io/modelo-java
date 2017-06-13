package io.redspark.domain.vet;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = Consulta.ENTITY_NAME, schema = Consulta.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Consulta.FIND_ALL, query = " SELECT u FROM Consulta u "),
		@NamedQuery(name = Consulta.FIND_BY_ID, query = " SELECT u FROM Consulta u WHERE u.id = :id "), })
public class Consulta {

	public static final String ENTITY_NAME = "consulta";
	public static final String SCHEMA_NAME = "public";
	public static final String FIND_ALL = "Consulta.findAll";
	public static final String FIND_BY_ID = "Consulta.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_veterinario")
	private Veterinario veterinario;

	@ManyToOne
	@JoinColumn(name = "id_animal")
	private Animal animal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_consulta", nullable = false, insertable = false, updatable = false)
	private Date dateConsulta = new Date();

	@OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL)
	private List<Agendamento> agendamentos = new ArrayList<>();

	public Consulta() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
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
