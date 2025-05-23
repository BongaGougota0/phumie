package za.co.phumie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.phumie.model.PhumieUser;

@Repository
public interface UserRepository extends JpaRepository<PhumieUser, Long> {

    PhumieUser findPhumieUserByUserEmail(String userEmail);

    PhumieUser findPhumieUserByUsername(String name);

    PhumieUser getPhumieUserByUserEmail(String userEmail);
}
