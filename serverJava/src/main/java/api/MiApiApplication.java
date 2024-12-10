package api;

import api.config.GrpcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@SpringBootApplication
public class MiApiApplication {
    public static void main(String[] args) {
        try {
            // Start Spring application context
            var context = SpringApplication.run(MiApiApplication.class, args);

            // Retrieve and start gRPC configuration
            var grpcConfig = context.getBean(GrpcConfig.class);
            grpcConfig.start();
            System.out.println("GRPC server started");
            grpcConfig.blockUntilShutdown();
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            System.err.println("Application interrupted: " + e.getMessage());
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3005");
            }
        };
    }
}

