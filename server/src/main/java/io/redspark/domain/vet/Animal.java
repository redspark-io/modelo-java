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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = Animal.ENTITY_NAME, schema = Animal.SCHEMA_NAME)
public class Animal implements Serializable {

	private static final long serialVersionUID = 5447700345782667759L;

	public static final String	ENTITY_NAME	= "animal";
	public static final String	SCHEMA_NAME	= "public";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@NotNull
	@Column(nullable = false, length = 100)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_dono", nullable = false)
	private Dono dono;

	@JsonIgnore
	@OneToMany(mappedBy = "animal", fetch = LAZY)
	private List<Agendamento> agendamentos = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "animal", fetch = LAZY)
	private List<Consulta> consultas = new ArrayList<>();

}
