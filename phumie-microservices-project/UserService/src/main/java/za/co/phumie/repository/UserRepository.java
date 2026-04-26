package za.co.phumie.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import za.phumie.shared.appmodels.PhumieUser;

@Repository
public interface UserRepository extends R2dbcRepository<PhumieUser, Long> {

    PhumieUser findPhumieUserByUserEmail(String userEmail);

    PhumieUser findPhumieUserByUsername(String name);

    PhumieUser getPhumieUserByUserEmail(String userEmail);
}
