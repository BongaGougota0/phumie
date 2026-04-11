package za.co.phumie.PostsService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.phumie.PostsService.model.Comment;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
