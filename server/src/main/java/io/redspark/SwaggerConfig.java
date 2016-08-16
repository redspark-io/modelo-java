package io.redspark;

import static com.google.common.base.Predicates.or;
import static io.redspark.AppProfile.DEV;
import static io.redspark.AppProfile.PRODUCAO;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Profile({DEV, PRODUCAO})
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(paths())    
	            .build()
            .pathMapping("/")
            .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
            "SESC REST API",
            "Modelo do SESC.",
            "1.0.0",
            "redspark.io",
            "API License",
            "API License URL",
            "/"
        );
        
        return apiInfo;
    }
    
	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return or(regex("/*.*"));
	}
}
