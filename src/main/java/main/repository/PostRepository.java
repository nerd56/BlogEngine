package main.repository;

import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    String postIsAvailable = "is_active = 1 AND moderation_status = 'ACCEPTED' AND posts.time <= NOW()";

    @Query(value = "SELECT * FROM posts WHERE id = ?1 AND " + postIsAvailable, nativeQuery = true)
    Post findAvailablePost(int id);

    @Query(value = "SELECT * FROM posts WHERE " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePosts();

    @Query(value = "SELECT * FROM posts WHERE " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePosts(Pageable pageable);

    @Query(value = "SELECT posts.* FROM posts JOIN tag2post ON posts.id = post_id JOIN tags ON tags.id = tag_id WHERE tags.name = ?1 AND " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePostsByTag(String tag, Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE time >= ?1 AND " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePostsByDate(LocalDate localDate, Pageable pageable);

    @Query(value = "SELECT * FROM posts WHERE text like ?1 or title like ?1", nativeQuery = true)
    List<Post> getAvailablePostsByQuery(String query, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM posts WHERE " + postIsAvailable, nativeQuery = true)
    int getAvailablePostsCount();

    @Query(value = "SELECT COUNT(*) FROM posts WHERE time >= ?1 AND " + postIsAvailable, nativeQuery = true)
    int getAvailablePostsByDateCount(LocalDate localDate);

    @Query(value = "SELECT COUNT(*) FROM posts JOIN tag2post ON posts.id = post_id JOIN tags ON tags.id = tag_id WHERE tags.name = ?1 AND " + postIsAvailable, nativeQuery = true)
    int getAvailablePostsByTagCount(String tag);

    @Query(value = "SELECT COUNT(*) FROM posts WHERE text like ?1 or title like ?1", nativeQuery = true)
    int getAvailablePostsByQueryCount(String query);

    @Query(value = "SELECT posts.* FROM posts LEFT JOIN post_votes ON posts.id = post_id AND value = 1 WHERE " + postIsAvailable + " GROUP BY posts.id ORDER BY COUNT(post_votes.id) DESC", nativeQuery = true)
    List<Post> getAvailablePostsSortedByLikeCount(Pageable pageable);

    @Query(value = "SELECT posts.* FROM posts LEFT JOIN post_comments ON posts.id = post_id WHERE " + postIsAvailable + " GROUP BY posts.id ORDER BY COUNT(post_comments.id) DESC", nativeQuery = true)
    List<Post> getAvailablePostsSortedByCommentsCount(Pageable pageable);
}
