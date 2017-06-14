package io.redspark.domain.vet;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Agendamento.ENTITY_NAME, schema = Agendamento.SCHEMA_NAME)
@NamedQueries({ @NamedQuery(name = Agendamento.FIND_ALL, query = " SELECT u FROM Agendamento u "), @NamedQuery(name = Agendamento.FIND_ALL_AGENDAMENTOS, query = " SELECT a FROM Agendamento a WHERE a.data = :data "), })
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 7515533818787442233L;

	public static final String	ENTITY_NAME						= "agendamento";
	public static final String	SCHEMA_NAME						= "public";
	public static final String	FIND_ALL							= "Agendamento.findAll";
	public static final String	FIND_ALL_AGENDAMENTOS	= "Agendamento.findAllAgendamento";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@NonNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_consulta")
	private Consulta consulta;

	@NonNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_vacina")
	private Vacina vacina;

	@NonNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id_animal")
	private Animal animal;

	@Temporal(TIMESTAMP)
	@Column(name = "data", nullable = false)
	private Date data = new Date();

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
