package com.lria62.QuestionForum.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Cấu hình cho tất cả các endpoint
                .allowedOrigins("http://localhost:63342")  // Cho phép yêu cầu từ frontend (localhost:3000)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức HTTP được phép
                .allowedHeaders("*")  // Cho phép tất cả các header
                .allowCredentials(true);  // Cho phép gửi cookie hoặc các header xác thực
    }
}
