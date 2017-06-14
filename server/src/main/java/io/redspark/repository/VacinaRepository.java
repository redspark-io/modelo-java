package io.redspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.redspark.domain.vet.Vacina;

public interface VacinaRepository  extends JpaRepository<Vacina, Long> {

}
