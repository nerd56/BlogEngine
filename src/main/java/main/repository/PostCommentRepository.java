package main.repository;

import main.model.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
    @Query(value = "select * from post_comments where post_id = ?1", nativeQuery = true)
    List<PostComment> getCommentsByPostId(int postId);
}
