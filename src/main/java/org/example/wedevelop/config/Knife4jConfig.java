package org.example.wedevelop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j 配置类  
 */  
@Configuration
public class Knife4jConfig {  
  
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()  
                .info(new Info()
                        .title("WeDevelop API 文档")  
                        .version("0.0.1")  
                        .description("WeDevelop 的 API 接口文档"));  
    }  
}