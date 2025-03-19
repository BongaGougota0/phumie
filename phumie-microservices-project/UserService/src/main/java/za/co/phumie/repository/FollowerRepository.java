package za.co.phumie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.phumie.model.Follower;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
}
