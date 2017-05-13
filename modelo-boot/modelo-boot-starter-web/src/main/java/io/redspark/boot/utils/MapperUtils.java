package io.redspark.boot.utils;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

public class MapperUtils<E, D> {

  public final MapperFactory factory;
  public final MapperFacade mapper;

  private Class<E> entityClass;
  private Class<D> dtoClass;

  public MapperUtils(Class<E> entityClass, Class<D> dtoClass) {
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;

    this.factory = this.createFactory();
    this.mapper = this.factory.getMapperFacade();
  }

  public MapperUtils(Class<E> entityClass, Class<D> dtoClass, CustomConverter<?, ?>... converters) {
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;

    this.factory = this.createFactory();
    this.registerConverters(converters);
    this.mapper = this.factory.getMapperFacade();
  }

  public D toDTO(E entity) {
    return mapper.map(entity, dtoClass);
  }

  public D toDTO(E entity, String... exclusions) {
    ClassMapBuilder<D, E> classMap = this.factory.classMap(dtoClass, entityClass);
    for (String exc : exclusions) {
      classMap.exclude(exc);
    }
    classMap.byDefault().register();
    return this.mapper.map(entity, dtoClass);
  }

  public E toEntity(D dto) {
    return mapper.map(dto, entityClass);
  }

  public E toEntity(D dto, String... exclusions) {
    ClassMapBuilder<E, D> classMap = this.factory.classMap(entityClass, dtoClass);
    for (String exc : exclusions) {
      classMap.exclude(exc);
    }
    classMap.byDefault().register();
    return this.mapper.map(dto, entityClass);
  }

  /**
   * Thread-safe. Create a new MapperFacade for every update
   */
  public void updateEntity(E entity, D dto, String... exclusions) {
    MapperFactory instanceFactory = this.createFactory();
    ClassMapBuilder<E, D> classMap = instanceFactory.classMap(entityClass, dtoClass);
    for (String exc : exclusions) {
      classMap.exclude(exc);
    }
    classMap.byDefault().register();

    instanceFactory.getMapperFacade(entityClass, dtoClass).mapReverse(dto, entity);
  }

  private MapperFactory createFactory() {
    return new DefaultMapperFactory.Builder().codeGenerationStrategy(new IgnoreLazyCodeGenerationStrategy()).build();
  }

  private void registerConverters(CustomConverter<?, ?>... converters) {

    ConverterFactory converterFactory = this.factory.getConverterFactory();

    for (CustomConverter<?, ?> converter : converters)
      converterFactory.registerConverter(converter);
  }
}
