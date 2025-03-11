package za.co.phumie.PostsService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.phumie.PostsService.model.Post;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
}
