package za.co.phumie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.phumie.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
