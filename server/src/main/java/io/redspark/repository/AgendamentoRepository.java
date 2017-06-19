package io.redspark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.redspark.domain.vet.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	@Query("select a from Agendamento a where UPPER(a.animal.nome) like UPPER(:search) or UPPER(a.vacina.nome) like UPPER(:search) ")
	Page<Agendamento> search(@Param("search") String value, Pageable page);

}
