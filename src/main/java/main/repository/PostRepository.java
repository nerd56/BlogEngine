package main.repository;

import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    String postIsAvailable = "is_active = 1 AND moderation_status = 'ACCEPTED' AND posts.time <= now()";

    @Query(value = "SELECT * FROM posts WHERE " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePosts();
    @Query(value = "SELECT * FROM posts WHERE " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePosts(Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM posts WHERE " + postIsAvailable, nativeQuery = true)
    int getAvailablePostsCount();

    @Query(value = "SELECT posts.* FROM posts LEFT JOIN post_votes ON posts.id = post_id AND value = 1 WHERE " + postIsAvailable + " GROUP BY posts.id ORDER BY COUNT(post_votes.id) DESC", nativeQuery = true)
    List<Post> getAvailablePostsSortedByLikeCount(Pageable pageable);
    @Query(value = "SELECT posts.* FROM posts LEFT JOIN post_comments ON posts.id = post_id WHERE " + postIsAvailable + " GROUP BY posts.id ORDER BY COUNT(post_comments.id) DESC", nativeQuery = true)
    List<Post> getAvailablePostsSortedByCommentsCount(Pageable pageable);
}
