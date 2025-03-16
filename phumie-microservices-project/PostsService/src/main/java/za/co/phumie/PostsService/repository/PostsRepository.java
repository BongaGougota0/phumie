package za.co.phumie.PostsService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.phumie.PostsService.model.Post;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
    Optional<Post> getPostByPostId(Long postId);

    @Query(nativeQuery = true, value = "SELECT * FROM Posts p WHERE p.author_user_id = :authorUserId")
    Page<Post> getPostByAuthorUserId(@Param("authorUserId") long authorUserId, Pageable pageable);

    Post findPostByPostId(long postId);
}
