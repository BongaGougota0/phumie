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
import za.co.phumie.exception.IncorrectLoginCredentials;
import za.co.phumie.mapper.UserMapper;
import za.co.phumie.security.JwtService;
import za.co.phumie.service.UsersService;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final WebClient.Builder webClient;
    private final JwtService jwtService;
    private final UsersService usersService;

    public AuthController(WebClient.Builder webClientConfig, JwtService jwtService, UsersService usersService) {
        this.webClient = webClientConfig;
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<AuthenticationDto>> signup(@RequestBody PhumieUserDto newUser) {
        ResponseDto resp = usersService.save(newUser);
        if(resp.getMessage().equalsIgnoreCase("Success")){
            return webClient
                    .build()
                    .post()
                    .uri("http://localhost:8080/api/users/login")
                    .bodyValue(new LoginCredentials(newUser.userEmail(), newUser.password()))
                    .retrieve()
                    .bodyToMono(AuthenticationDto.class)
                    .map(authDto -> new ResponseEntity<>(authDto, HttpStatus.OK))
                    .onErrorResume(e -> Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));
        } else {
            return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody LoginCredentials loginCredentials){
        if(usersService.authenticateUser(loginCredentials)){
            var userEmailUsernameObj = new PhumieUserDto(loginCredentials.usernameEmail(),
                    loginCredentials.usernameEmail(), loginCredentials.password(),"","");
            var userObject = usersService.getUserByEmailOrUsername(userEmailUsernameObj);
            var userDto = UserMapper.mapEntityToDto(userObject);
            String jwtToken = jwtService.generateToken(userDto);
            var responseData = new AuthenticationDto(jwtToken, userDto);
            return ResponseEntity.ok(responseData);
        } else {
            throw new IncorrectLoginCredentials("Invalid credentials");
        }
    }
}