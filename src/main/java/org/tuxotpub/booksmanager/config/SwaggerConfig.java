package org.tuxotpub.booksmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created by tuxsamo.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .tags(new Tag("AuthorsDescription", "Authors books-manager of tuxotpub.org."),
                        new Tag("BooksDescription", "Books books-manager of tuxotpub.org"),
                        new Tag("MagazinesDescription", "Magazines books-manager of tuxotpub.org."));
    }

    private ApiInfo apiInfo(){

        Contact contact = new Contact("Osman Tartoussi", "https://tuxotpub.org",
                "tuxotpub@tuxotpub.com");

        return new ApiInfo(
                "Books-manager Tuxotpub",
                "Books-manager management of Tuxotpub",
                "1.0",
                "Terms of Service: ...",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}