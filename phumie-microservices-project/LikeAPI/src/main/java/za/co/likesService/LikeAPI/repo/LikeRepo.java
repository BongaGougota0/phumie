package za.co.likesService.LikeAPI.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import za.phumie.shared.appmodels.PostLike;

@Repository
public interface LikeRepo extends R2dbcRepository<PostLike,Long> {
}
