package io.redspark.autoconfigure.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnClass(Docket.class)
@ConditionalOnProperty("swagger.base.package")
public class SwaggerAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(ApiInfo.class)
	public Docket api(ApiInfo apiInfo, @Value("${swegger.base.package") String basePackage) {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage(basePackage))
	      .paths(PathSelectors.any())                          
	      .build()
	      .apiInfo(apiInfo);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public Docket apiDefault(@Value("${swagger.base.package}") String basePackage) {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage(basePackage))
	      .paths(PathSelectors.any())                          
	      .build();
	}
}

