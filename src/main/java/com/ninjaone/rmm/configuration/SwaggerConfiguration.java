package com.ninjaone.rmm.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.ninjaone.rmm.exception.ExceptionResponse;
import com.ninjaone.rmm.exception.RecordNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private ApiInfo apiInfo() {
        return new ApiInfo("BACKEND ENGINEER TASK FOR NINJAONE API", "This is a technical test for senior backend developer role.", "1.0", null,
                new Contact("Felipe Díaz C", "https://www.linkedin.com/in/felipediazc/", "felipediazc@gmail.com"),
                null, null, Collections.emptyList());
    }

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(RecordNotFoundException.class),
                        typeResolver.resolve(ExceptionResponse.class)
                )
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ninjaone.rmm"))
                .build()
                .useDefaultResponseMessages(false);
    }
}