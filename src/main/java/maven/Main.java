package maven;

import org.springframework.boot.SpringApplication;

/**
 * Created by pisarenko on 11.01.2017.
 */
//@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MavenController.class, args);
    }
/*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("OPTIONS")
                        .allowedOrigins("*");
            }
        };
    }
*/
}
