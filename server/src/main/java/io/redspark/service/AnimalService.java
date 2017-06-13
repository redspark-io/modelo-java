package io.redspark.service;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.redspark.controller.form.AnimalForm;
import io.redspark.domain.vet.Animal;
import io.redspark.domain.vet.Dono;
import io.redspark.exception.WebException;
import io.redspark.repository.AnimalRepository;
import io.redspark.repository.DonoRepository;

// @Service === @Stateless
@Service
public class AnimalService {

  // @Inject || @EJB
  @Autowired
  private AnimalRepository repository;

  @Autowired
  private DonoRepository donoRepository;

  public Animal findOne(Long id) {
    return repository.findOne(id);
  }

  @Transactional(readOnly = true)
  public Animal findByDono(Dono dono) {
    return repository.findByDono(dono);
  }

  @Transactional
  public Animal insert(AnimalForm form) {

    Dono dono = donoRepository.findOne(form.getDonoId());

    if (isNull(dono))
      throw new WebException(BAD_REQUEST, "Valores inválidos");

    Animal animal = Animal.builder()
        .nome(form.getName())
        .dono(dono)
        .build();

    animal = repository.save(animal);

    return animal;
  }

  @Transactional
  public Animal update(Long id, AnimalForm form) {

    Animal animal = repository.findOne(id);

    Dono dono = donoRepository.findOne(form.getDonoId());

    if (isNull(animal) || isNull(dono))
      throw new WebException(BAD_REQUEST, "Valores inválidos");

    animal.setDono(dono);
    animal.setNome(form.getName());

    repository.save(animal);

    return animal;
  }

  @Transactional(readOnly = true)
  public List<Animal> findAll() {
    return repository.findAll();
  }
}
