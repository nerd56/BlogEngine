package main.repository;

import main.model.Tag2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {
    @Query(value = "select * from tag2post where post_id = ?1", nativeQuery = true)
    List<Tag2Post> getTag2PostByPostId(int postId);
}
