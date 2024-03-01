package tn.esprit.foyeruniversiteeya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("Authorization") // Expose any additional headers if needed
                .allowCredentials(true)
                .maxAge(3600); // Configure the max age of preflight requests (if required)
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new CorsConfig();
    }
}
