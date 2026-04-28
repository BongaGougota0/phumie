package za.phumie.shared.appdtos;

import reactor.core.publisher.Mono;
import za.phumie.shared.mapper.ApplicationMapper;

public record AuthenticationDto(String jwt, Mono<ApplicationMapper.PhumieUserDto> phumieUserDto) {
}
