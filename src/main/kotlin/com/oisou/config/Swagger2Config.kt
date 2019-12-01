package com.oisou.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact

@Configuration
@EnableSwagger2
class Swagger2Config {
    @Bean
    fun api():Docket{
        return Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.oisou")).paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo())
    }

    private fun apiEndPointsInfo(): ApiInfo {
        return ApiInfoBuilder().title("Spring Boot REST API")
            .description("Oisuo REST API")
            .contact(Contact("Ali Wassouf", "", "ali.wassouf.oisou@gmail.com"))
            .version("0.0.1")
            .build()
    }
}