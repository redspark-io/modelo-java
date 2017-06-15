package io.redspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.redspark.domain.vet.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
