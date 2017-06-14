package io.redspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.redspark.domain.vet.Consulta;

public interface ConsultaRepository  extends JpaRepository<Consulta, Long> {

}
