package com.app.api.gestao.configuration;

import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static io.swagger.models.auth.In.HEADER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", AUTHORIZATION, HEADER.name())))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .tags(new Tag("usuario", "Operações com usuários"))
                .tags(new Tag("gasto", "Operações com gasto"))
                .tags(new Tag("categoria", "Operações com categoria"))
                .genericModelSubstitutes(Optional.class);

    }

    private List<SecurityScheme> securitySchemes() {
       return new ArrayList<>(Arrays.asList(new ApiKey("Bearer %token", "Authorization", "header")));
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("API gestao de gastos")
                .description(
                        "Essa é uma API desenvolvida para teste das gestao de gastos, utiliza autenticação JWT. " +
                                "Você encontra mais detalhes sobre JWT em [https:jwt.io/](https:jwt.io/). " +
                                "Neste exemplo, você pode utilizar as credenciais 'admin' com senha 'admin' ou 'client' com senha 'client'. " +
                                "Assim que logado (usuarios -> logar), você obterá um token, basta clicar no botão a direita em `Authorize` e colocar o prefixo " +
                                "\"Bearer \" antes de seu token. " +
                                "Também é possivel efetuar o cadastro de novos cliente/empresas, utilizando (usuario -> cadastrar)")
                .version("1.0.0")
                .contact(new Contact(null, null, "rafael.goncalves@live.com"))
                .build();
    }

}
