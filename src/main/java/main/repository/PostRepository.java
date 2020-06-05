package main.repository;

import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    String postIsAvailable = "is_active = 1 and moderation_status = 'ACCEPTED' and posts.time <= now()";

    @Query(value = "select * from posts where " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePosts();
    @Query(value = "select * from posts where " + postIsAvailable, nativeQuery = true)
    List<Post> getAvailablePosts(Pageable pageable);
    @Query(value = "select * from posts where time >= ?1 and " + postIsAvailable,nativeQuery = true)
    List<Post> getAvailablePostsByDate(String date);
    @Query(value = "select posts.* from posts join tag2post on post_id = posts.id join tags on tag_id = tags.id and name = ?1",nativeQuery = true)
    List<Post> getAvailablePostsByTag(String tag);
    @Query(value = "select count(*) from posts",nativeQuery = true)
    int getSize();

    @Query(value = "select count(*) from post_votes where value = 1 and post_id = ?1", nativeQuery = true)
    int getLikeCount(int id);
    @Query(value = "select count(*) from post_votes where value = -1 and post_id = ?1", nativeQuery = true)
    int getDislikeCount(int id);
    @Query(value = "select count(*) from post_comments where post_id = ?1", nativeQuery = true)
    int getCommentCount(int id);

    @Query(value = "select posts.* from posts left join post_votes on posts.id = post_id and value = 1 where " + postIsAvailable + " group by posts.id order by count(post_votes.id) desc", nativeQuery = true)
    List<Post> getAvailablePostsSortedByLikeCount(Pageable pageable);
    @Query(value = "select posts.* from posts left join post_comments on posts.id = post_id where " + postIsAvailable + " group by posts.id order by count(post_comments.id) desc", nativeQuery = true)
    List<Post> getAvailablePostsSortedByCommentsCount(Pageable pageable);
}
