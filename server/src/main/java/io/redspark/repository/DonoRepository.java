package io.redspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.redspark.domain.vet.Dono;

public interface DonoRepository extends JpaRepository<Dono, Long> {

}
