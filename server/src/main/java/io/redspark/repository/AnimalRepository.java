package io.redspark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Dono;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

  Animal findByDono(Dono dono);

  void findByConsultasIn(Object object);

}
