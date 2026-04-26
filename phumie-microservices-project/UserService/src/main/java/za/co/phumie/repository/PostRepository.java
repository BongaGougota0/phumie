package za.co.phumie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import za.phumie.shared.appmodels.Post;
import java.util.Optional;

@Repository
public interface PostRepository extends R2dbcRepository<Post, Long> {
    Optional<Post> getPostByPostId(Long postId);

    @Query(value = "SELECT * FROM Posts p WHERE p.author_user_id = :authorUserId")
    Flux<Post> getPostByAuthorUserId(@Param("authorUserId") long authorUserId, Pageable pageable);

    Post findPostByPostId(long postId);
}
