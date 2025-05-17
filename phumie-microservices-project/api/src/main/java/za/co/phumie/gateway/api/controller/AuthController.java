package za.co.phumie.gateway.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import za.co.phumie.gateway.api.dto.AuthResponse;
import za.co.phumie.gateway.api.dto.LoginCredentials;
import za.co.phumie.gateway.api.dto.ResponseDto;
import za.co.phumie.gateway.api.models.PhumieUserDto;
import za.co.phumie.gateway.api.service.JwtAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtAuthService jwtAuthService;
    private final WebClient.Builder webClientBuilder;

    public AuthController(JwtAuthService jwtAuthService, WebClient.Builder webClientBuilder) {
        this.jwtAuthService = jwtAuthService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginCredentials credentials) {
        return webClientBuilder.build()
                .post()
                .uri("lb://user-service/api/users/validate")
                .bodyValue(credentials)
                .retrieve()
                .bodyToMono(PhumieUserDto.class)
                .map(userDto -> {
                    // Generate JWT using the API Gateway's JWT service
                    String token = jwtAuthService.generateToken(userDto);
                    return ResponseEntity.ok(new AuthResponse(token, userDto.userEmail()));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<AuthResponse>> signup(@RequestBody PhumieUserDto userDto) {
        return webClientBuilder.build()
                .post()
                .uri("lb://user-service/api/users")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .flatMap(response -> {
                    // If successful signup, get user details
                    return webClientBuilder.build()
                            .post()
                            .uri("lb://user-service/api/users/validate")
                            .bodyValue(new LoginCredentials(userDto.userEmail(), userDto.password()))
                            .retrieve()
                            .bodyToMono(PhumieUserDto.class);
                })
                .map(createdUser -> {
                    // Generate JWT for new user
                    String token = jwtAuthService.generateToken(createdUser);
                    return ResponseEntity.ok(new AuthResponse(token, createdUser.userEmail()));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
