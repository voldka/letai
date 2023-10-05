package com.example.letai.config.swagger;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Profile("swagger-enabled-for-qa")
@Configuration
//enable the Swagger 2
@EnableSwagger2
public class SwaggerConfig {

    //    *Docket bean* is defined and using its *select()* method, get an instance of *ApiSelectorBuilder*.
//    *ApiSelectorBuilder* we configure the *endpoints* exposed by Swagger.
//    *ApiSelectorBuilder*, which provides a way to *control the endpoints* exposed by Swagger.
    @Bean
    public Docket postsApi() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2);

        docket.groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();


        docket.globalResponseMessage(RequestMethod.GET, ImmutableList.of(new ResponseMessageBuilder()
                .code(400)
                .message("Bad Request")
                .responseModel(new ModelRef("Error")).build(),new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .responseModel(new ModelRef("Error")).build()));

        return docket;
    }

    //    Using the *RequestHandlerSelectors* and *PathSelectors*
//    we *configure the predicates* for selection of RequestHandlers.
    private Predicate<String> postPaths() {
        return or(regex("/api/posts.*"), regex("/api/javainuse.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("JavaInUse API")
                .description("JavaInUse API reference for developers")
                .termsOfServiceUrl("http://javainuse.com")
                .contact("javainuse@gmail.com").license("JavaInUse License")
                .licenseUrl("javainuse@gmail.com").version("1.0").build();
    }

}