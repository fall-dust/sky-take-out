package com.sky.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Swagger配置类，Swagger配置配置可以使用注解配置，也可以在Springboot配置文件中配置。
 *
 * @author 高翔宇
 * @since 2023/11/7 周二 18:00
 */
@SpringBootConfiguration
public class SwaggerConfiguration {
    /**
     * 分组配置。也可以在 `application.yml`/`springdoc.group-configs` 中的 `springdoc.group-configs` 属性下配置。
     *
     * @return {@link GroupedOpenApi}
     */
    @Bean
    public GroupedOpenApi adminEmployeeApi() {
        return GroupedOpenApi.builder()
                .group("employee")
                .displayName("员工管理")
                .pathsToMatch("/admin/employee/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.addSecurityItem(new SecurityRequirement());
                })
                .build();
    }

    @Bean
    public GroupedOpenApi adminDishCategoryApi() {
        return GroupedOpenApi.builder()
                .group("category")
                .displayName("菜品类别管理")
                .pathsToMatch("/admin/category/**")
                //.addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class))
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        // TODO 在Swagger UI界面测试API时，暂时无法实现在请求中添加授权标头（token）
        return new OpenAPI()
                .info(new Info().title("苍穹外卖")
                        .description("苍穹外卖API接口文档，基于Springdoc-Swagger3")
                        .version("v0.0.1")
                        .license(new License()
                                .name("许可证")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Gitee源码地址")
                        .url("https://gitee.com/a2176281647/sky_take_out.git"))
                .components(new Components().addSecuritySchemes("token", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}