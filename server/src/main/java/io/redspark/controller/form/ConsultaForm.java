package io.redspark.controller.form;

import java.util.Date;

import lombok.Builder;

@Builder
public class ConsultaForm {

	private Long			idAnimal;
	private Long			idVeterinario;
	private Date	dateConsulta;

	public ConsultaForm(Long idAnimal, Long idVeterinario, Date dateConsulta) {
		super();
		this.idAnimal = idAnimal;
		this.idVeterinario = idVeterinario;
		this.dateConsulta = dateConsulta;
	}

	public Long getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(Long idAnimal) {
		this.idAnimal = idAnimal;
	}

	public Long getIdVeterinario() {
		return idVeterinario;
	}

	public void setIdVeterinario(Long idVeterinario) {
		this.idVeterinario = idVeterinario;
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
		result = prime * result + ((dateConsulta == null) ? 0 : dateConsulta.hashCode());
		result = prime * result + ((idAnimal == null) ? 0 : idAnimal.hashCode());
		result = prime * result + ((idVeterinario == null) ? 0 : idVeterinario.hashCode());
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
		ConsultaForm other = (ConsultaForm) obj;
		if (dateConsulta == null) {
			if (other.dateConsulta != null)
				return false;
		} else if (!dateConsulta.equals(other.dateConsulta))
			return false;
		if (idAnimal == null) {
			if (other.idAnimal != null)
				return false;
		} else if (!idAnimal.equals(other.idAnimal))
			return false;
		if (idVeterinario == null) {
			if (other.idVeterinario != null)
				return false;
		} else if (!idVeterinario.equals(other.idVeterinario))
			return false;
		return true;
	}

}
