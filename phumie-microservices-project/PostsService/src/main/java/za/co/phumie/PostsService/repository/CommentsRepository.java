package za.co.phumie.PostsService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.phumie.PostsService.model.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
