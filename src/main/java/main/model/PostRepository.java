package main.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query(value = "select * from posts where is_active = 1 and moderation_status = 'ACCEPTED' and time <= now()",nativeQuery = true)
    List<Post> getAvailablePosts();
    @Query(value = "select * from posts where time >= ?1 and is_active = 1 and moderation_status = 'ACCEPTED' and time <= now()",nativeQuery = true)
    List<Post> getAvailablePostsByDate(String date);
    @Query(value = "select posts.* from posts join tag2post on post_id = posts.id join tags on tag_id = tags.id and name = ?1",nativeQuery = true)
    List<Post> getAvailablePostsByTag(String tag);
    @Query(value = "select count(*) from posts",nativeQuery = true)
    int getSize();
}
