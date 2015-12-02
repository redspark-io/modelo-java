package io.redspark.utils.mapper;

import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultCodeGenerationStrategy;
import ma.glasnost.orika.impl.generator.SourceCodeContext;
import ma.glasnost.orika.impl.generator.Specification;
import ma.glasnost.orika.impl.generator.VariableRef;
import ma.glasnost.orika.impl.generator.specification.AbstractSpecification;
import ma.glasnost.orika.metadata.FieldMap;

public class IgnoreLazyCodeGenerationStrategy extends DefaultCodeGenerationStrategy {

  public IgnoreLazyCodeGenerationStrategy() {
    super();
    List<Specification> list = super.getSpecifications();
    List<Specification> specs = new ArrayList<Specification>();

    for (Specification spec : list) {
      specs.add(new IfInitializedHibernateSpecification(spec));
    }

    super.getSpecifications().clear();
    super.getSpecifications().addAll(specs);
  }

  public static class IfInitializedHibernateSpecification extends AbstractSpecification {

    private Specification delegate;

    public IfInitializedHibernateSpecification(Specification delegate) {
      this.delegate = delegate;
    }

    @Override
    public boolean appliesTo(FieldMap fieldMap) {
      return delegate.appliesTo(fieldMap);
    }

    @Override
    public String generateMappingCode(FieldMap fieldMap, VariableRef source, VariableRef destination,
        SourceCodeContext code) {
      StringBuilder sb = new StringBuilder();

      sb.append(String.format("if(org.hibernate.Hibernate.isInitialized(%s)) {", source.asWrapper()));
      sb.append(delegate.generateMappingCode(fieldMap, source, destination, code));
      sb.append("}\n");

      return sb.toString();
    }

    @Override
    public void setMapperFactory(MapperFactory mapperFactory) {
      super.setMapperFactory(mapperFactory);
      delegate.setMapperFactory(mapperFactory);
    }

  }
}
