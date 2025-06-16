package grepp.NBE5_6_2_Team03.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://v0-hello-in-korean.vercel.app")
                .allowedMethods("GET", "POST", "PUT","PATCH","DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
