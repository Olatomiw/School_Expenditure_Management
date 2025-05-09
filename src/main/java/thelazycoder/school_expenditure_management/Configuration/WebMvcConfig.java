package thelazycoder.school_expenditure_management.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000", "https://6adcf1d7-30e3-4673-a5cf-1a2f6134e59b.lovableproject.com", "https://preview--flexispend-login-hub.lovable.app" )
                .allowedHeaders("Content-Type", "Authorization")
                .allowedMethods("PUT", "DELETE", "POST", "GET")
                .allowCredentials(true);

    }
}
