package io.redspark.controller.comparator;

import java.util.Collections;
import java.util.Comparator;

import io.redspark.controller.dto.AgendamentoDTO;

public enum AgendamentoDTOComparator implements Comparator<AgendamentoDTO> {

	/** Modo de Usar: lista.sort(AgendamentoDTOComparator.PorData.asc()); */
	/** Modo de Usar: lista.sort(AgendamentoComparator.PorVacina.asc()); */

	PorData() {
		public int compare(AgendamentoDTO one, AgendamentoDTO other) {
			return one.getData().compareTo(other.getData());
		}
	},
//	PorVacina() {
//		public int compare(AgendamentoDTO one, AgendamentoDTO other) {
//			return String.CASE_INSENSITIVE_ORDER.compare(one.getVacina().getNome(), other.getVacina().getNome());
//		}
//	}
;

	public abstract int compare(AgendamentoDTO one, AgendamentoDTO other);

	public Comparator<AgendamentoDTO> asc() {
		return this;
	}

	public Comparator<AgendamentoDTO> desc() {
		return Collections.reverseOrder(this);
	}

}