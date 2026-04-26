package za.co.phumie.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.phumie.shared.appmodels.Comment;
import java.util.List;

@Repository
public interface CommentRepository extends R2dbcRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comments c WHERE c.post_id = :postId")
    List<Comment> getPostComments(@Param("postId") long postId);
}
