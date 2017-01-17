package maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by pisarenko on 11.01.2017.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .allowedHeaders(
                                "Access-Control-Request-Headers",
                                "Access-Control-Request-Method",
                                "Host",
                                "Connection",
                                "Origin",
                                "User-Agent",
                                "Accept",
                                "Referer",
                                "Accept-Encoding",
                                "Accept-Language",
                                "Access-Control-Allow-Origin"
                        )
                        .allowCredentials(true);
            }
        };
    }
}
