package za.co.phumie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import za.co.phumie.dto.AuthenticationDto;
import za.co.phumie.dto.LoginCredentials;
import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.service.UsersServiceImpl;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final WebClient.Builder webClient;
    private final UsersServiceImpl usersServiceImpl;

    public AuthController(WebClient.Builder webClientConfig, UsersServiceImpl usersServiceImpl) {
        this.webClient = webClientConfig;
        this.usersServiceImpl = usersServiceImpl;
    }

    // Signup user and login user.
    // In case of existing username or email. Application throws error 409 Conflict.
    @PostMapping(value = "/signup")
    public Mono<ResponseEntity<AuthenticationDto>> signup(@RequestBody PhumieUserDto newUser) {
        ResponseDto resp = usersServiceImpl.save(newUser);
        if(resp.getMessage().equalsIgnoreCase("Success")){
            return webClient
                    .build()
                    .post()
                    .uri("http://localhost:8080/api/auth/login")
                    .bodyValue(new LoginCredentials(newUser.userEmail(), newUser.password()))
                    .retrieve()
                    .bodyToMono(AuthenticationDto.class)
                    .map(authDto -> new ResponseEntity<>(authDto, HttpStatus.OK))
                    .onErrorResume(e -> Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));
        } else {
            return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginCredentials loginCredentials){
            var responseData = usersServiceImpl.authenticateUser(loginCredentials);
            return ResponseEntity.ok(responseData);
    }
}