package za.co.phumie.PostsService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.phumie.PostsService.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
