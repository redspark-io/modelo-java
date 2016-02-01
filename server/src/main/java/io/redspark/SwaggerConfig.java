package io.redspark;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

@Configuration
@EnableSwagger
public class SwaggerConfig implements ServletContextAware {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(final SpringSwaggerConfig springSwaggerConfig) {
	this.springSwaggerConfig = springSwaggerConfig;
    }

    @Value("${swagger.resource_prefix}")
    private String prefix;

    private ServletContext servletContext;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {

	final RelativeSwaggerPathProvider relativeSwaggerPathProvider = new RelativeSwaggerPathProvider(servletContext);
	relativeSwaggerPathProvider.setApiResourcePrefix(prefix);
	return new SwaggerSpringMvcPlugin(springSwaggerConfig).pathProvider(relativeSwaggerPathProvider)
	.apiInfo(apiInfo()).includePatterns("/me.*", "/usuario.*", "/unidades.*").apiVersion("1.0.5");
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
	this.servletContext = servletContext;
    }

    private ApiInfo apiInfo() {
	final ApiInfo apiInfo = new ApiInfo("SESC-Seta API", "API for SESC-Seta", "SESC-Seta API terms of service",
		null, "SESC-Seta API Licence Type", "SESC-Seta API License URL");
	return apiInfo;
    }

}
