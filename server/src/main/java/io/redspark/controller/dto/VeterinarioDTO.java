package io.redspark.controller.dto;

import io.redspark.domain.vet.Consulta;
import io.redspark.domain.vet.Veterinario;
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
public class VeterinarioDTO {

	private Long id;
	private String nome;

	public VeterinarioDTO(Veterinario veterinario) {
		this.id = veterinario.getId();
		this.nome = veterinario.getNome();
	}

}