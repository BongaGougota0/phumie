package za.co.phumie.PostsService.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import za.phumie.shared.appmodels.Post;

@Repository
public interface PostsRepository extends R2dbcRepository<Post, Long> {
//    Optional<Post> getPostByPostId(Long postId);
//
//    @Query(nativeQuery = true, value = "SELECT * FROM Posts p WHERE p.author_user_id = :authorUserId")
//    Page<Post> getPostByAuthorUserId(@Param("authorUserId") long authorUserId, Pageable pageable);
//
//    Post findPostByPostId(long postId);
}
