package za.co.phumie.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import za.phumie.shared.appmodels.PhumieUser;

@Repository
public interface UserRepository extends R2dbcRepository<PhumieUser, Long> {

    Mono<PhumieUser> findByUserEmail(String userEmail);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByUserEmail(String s);

    Mono<PhumieUser> findByUsername(String oldUsername);
}
