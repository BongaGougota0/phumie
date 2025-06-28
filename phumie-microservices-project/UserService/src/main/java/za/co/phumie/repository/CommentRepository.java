package za.co.phumie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.phumie.model.Comment;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM comments c WHERE c.post_id = :postId")
    List<Comment> getPostComments(@Param("postId") long postId);
}
