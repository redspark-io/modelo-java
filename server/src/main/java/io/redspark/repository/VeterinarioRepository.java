package io.redspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.redspark.domain.vet.Veterinario;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

}
