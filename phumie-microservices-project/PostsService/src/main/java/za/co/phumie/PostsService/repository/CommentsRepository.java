package za.co.phumie.PostsService.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import za.phumie.shared.appmodels.Comment;

@Repository
public interface CommentsRepository extends R2dbcRepository<Comment, Long> {
}
