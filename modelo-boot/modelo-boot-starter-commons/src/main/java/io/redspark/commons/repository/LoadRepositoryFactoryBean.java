package io.redspark.commons.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class LoadRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
    extends JpaRepositoryFactoryBean<R, T, I> {

  public LoadRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
  @SuppressWarnings("rawtypes")
  protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
    return new LoadRepositoryFactory(em);
  }

  private static class LoadRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

    private final EntityManager em;

    public LoadRepositoryFactory(EntityManager em) {

      super(em);
      this.em = em;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object getTargetRepository(RepositoryInformation information) {
      return new LoadRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      return LoadRepositoryImpl.class;
    }
  }
}