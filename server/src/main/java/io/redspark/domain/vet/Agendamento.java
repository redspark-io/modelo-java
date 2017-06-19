package io.redspark.domain.vet;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Table(name = Agendamento.ENTITY_NAME, schema = Agendamento.SCHEMA_NAME)
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 7515533818787442233L;

	public static final String	ENTITY_NAME	= "agendamento";
	public static final String	SCHEMA_NAME	= "public";

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

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

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@Column(name = "data", nullable = false)
	private LocalDateTime data;

}
