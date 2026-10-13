package org.sopt.global.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    // API 기본 설정 정보
    private Info apiInfo() {
        return new Info()
                .title("이수의 API 명세서")
                .description("SOPT 39기 서버 과제 API 명세서 문서입니다.")
                .version("v1.0.0");
    }
}
