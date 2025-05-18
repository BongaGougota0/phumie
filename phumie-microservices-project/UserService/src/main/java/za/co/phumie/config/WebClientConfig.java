package za.co.phumie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> {
                    // Log all outgoing requests
                    System.out.println("Outgoing request: " + request.method() + " " + request.url());
                    return next.exchange(request);
                });
    }s

}
