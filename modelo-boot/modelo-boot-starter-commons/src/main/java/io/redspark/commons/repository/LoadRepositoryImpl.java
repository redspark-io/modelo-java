package io.redspark.commons.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import io.redspark.commons.exception.NotFoundException;

public class LoadRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements LoadRepository<T, ID> {

  private Class<T> domainClass;

  public LoadRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
    super(domainClass, entityManager);

    this.domainClass = domainClass;
  }

  @Override
  public T load(ID id) {
    T object = findOne(id);

    if (object == null) {
      throw new NotFoundException(domainClass);
    }

    return object;
  }
}