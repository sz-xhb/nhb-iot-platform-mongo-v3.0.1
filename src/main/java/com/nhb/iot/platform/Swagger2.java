package com.nhb.iot.platform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.nhb.iot.platform"))
        .paths(PathSelectors.any()).build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("平台系统接口文档").description("平台系统")
        .termsOfServiceUrl("http://www.newhongbo.com")
        .contact(new Contact("XS guo", "http://www.newhongbo.com", "gxs@newhongbo.com"))
        .version("3.0.1").build();
  }
}
