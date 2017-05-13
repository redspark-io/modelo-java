package io.redspak.boot.custom.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LoadRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

  T load(ID id);
}