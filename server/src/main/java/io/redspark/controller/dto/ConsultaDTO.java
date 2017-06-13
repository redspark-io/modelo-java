package io.redspark.controller.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConsultaDTO {

	private Integer idAnimal;
	private Integer idVeterinario;
	private Date dataConsulta;
	private Map<Date, Integer> agendamentos = new HashMap<>();

	public ConsultaDTO() {
	}
	

	public Integer getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(Integer idAnimal) {
		this.idAnimal = idAnimal;
	}

	public Integer getIdVeterinario() {
		return idVeterinario;
	}

	public void setIdVeterinario(Integer idVeterinario) {
		this.idVeterinario = idVeterinario;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public Map<Date, Integer> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(Map<Date, Integer> agendamentos) {
		this.agendamentos = agendamentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataConsulta == null) ? 0 : dataConsulta.hashCode());
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
		ConsultaDTO other = (ConsultaDTO) obj;
		if (dataConsulta == null) {
			if (other.dataConsulta != null)
				return false;
		} else if (!dataConsulta.equals(other.dataConsulta))
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