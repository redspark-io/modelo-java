package io.redspark.controller.dto;

import io.redspark.domain.vet.Dono;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonoDTO {

	private Long id;
	private String nome;
	private String email;

	public DonoDTO(Dono dono) {
		this.id = dono.getId();
		this.nome = dono.getNome();
		this.email = dono.getEmail();
	}

}