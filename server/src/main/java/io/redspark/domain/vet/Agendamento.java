package io.redspark.domain.vet;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = Agendamento.ENTITY_NAME, schema = Agendamento.SCHEMA_NAME)
@NamedQueries({ 
	@NamedQuery(name = Agendamento.FIND_ALL, query = " SELECT u FROM Agendamento u "), 
	@NamedQuery(name = Agendamento.FIND_ALL_AGENDAMENTOS, query = " SELECT a FROM Agendamento a WHERE a.data = :data "), 
	})
public class Agendamento {

	public static final String ENTITY_NAME = "agendamento";
	public static final String SCHEMA_NAME = "public";
	public static final String FIND_ALL = "Agendamento.findAll";
	public static final String FIND_ALL_AGENDAMENTOS = "Agendamento.findAllAgendamento";

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_consulta")
	private Consulta consulta;

	@ManyToOne
	@JoinColumn(name = "id_vacina")
	private Vacina vacina;

	@ManyToOne
	@JoinColumn(name = "id_animal")
	private Animal animal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false, insertable = false, updatable = false)
	private Date data = new Date();

	public Agendamento() {

	}

	public Agendamento(Consulta consulta, Vacina vacina, Animal animal) {
		super();
		this.consulta = consulta;
		this.vacina = vacina;
		this.animal = animal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Date getData() {
		return data;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public void setData(Date data) {
		this.data = data;
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
		Agendamento other = (Agendamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
