package za.co.phumie.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import za.phumie.shared.appmodels.Follower;

@Repository
public interface FollowerRepository extends R2dbcRepository<Follower, Long> {
}
