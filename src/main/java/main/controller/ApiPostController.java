package main.controller;

import main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.*;

@RestController
public class ApiPostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/api/post/")
    public Map<String, Object> getApiPost(@RequestParam("offset") int offset,
                                              @RequestParam("limit") int limit,
                                              @RequestParam("mode") String mode) {
        Map<String, Object> map = new HashMap<>();
        List<Post> posts = postRepository.getAvailablePosts();

        if (mode.equals("recent")) posts.sort(Comparator.comparing(Post::getTime).reversed());
        else if (mode.equals("best")) posts.sort(Comparator.comparing(Post::getLikeCount).reversed());
        else if (mode.equals("popular")) posts.sort(Comparator.comparing(Post::getCommentCount).reversed());
        else if (mode.equals("early")) posts.sort(Comparator.comparing(Post::getTime));

        int count = posts.size();
        int end = Math.min(count, offset+limit);
        List<Map<String, Object>> postResponse = new ArrayList<>();
        posts.subList(offset, end).forEach(p -> postResponse.add(p.getGeneralInformation(userRepository)));

        map.put("count", count);
        map.put("posts", postResponse);
        return map;
    }

    @GetMapping("/api/post/search")
    public Map<String, Object> getApiPostSearch(@RequestParam("offset") int offset,
                                                @RequestParam("limit") int limit,
                                                @RequestParam("query") String query) {
        if (query.isEmpty()) return getApiPost(offset, limit, "");
        Map<String, Object> map = new HashMap<>();
        List<Post> posts = new ArrayList<>();
        entityManager.createNativeQuery("select * from (" + query + ") as t where is_active = 1 and moderation_status = 'ACCEPTED' and time <= now()",
                Post.class).getResultList().forEach(p -> posts.add(((Post) p)));

        int count = posts.size();
        int end = Math.min(count, offset+limit);
        List<Map<String, Object>> postResponse = new ArrayList<>();
        posts.subList(offset, end).forEach(p -> postResponse.add(p.getGeneralInformation(userRepository)));

        map.put("count", count);
        map.put("posts", postResponse);
        return map;
    }

    @GetMapping("/api/post/{id}")
    public Map<String, Object> getApiPostId(@PathVariable("id") int id) {
        Post post = postRepository.findById(id).get();
        Map<String, Object> postResponse = post.getDetailedInformation(userRepository, postCommentRepository, tagRepository);
        return postResponse;
    }

    @GetMapping("/api/post/byDate/")
    public Map<String, Object> getApiPostByDate(@RequestParam("offset") int offset,
                                                @RequestParam("limit") int limit,
                                                @RequestParam("date") String date) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> postsInformation = new ArrayList<>();
        postRepository.getAvailablePostsByDate(date).forEach(p -> postsInformation.add(p.getGeneralInformation(userRepository)));
        List<Map<String, Object>> l = postsInformation.subList(offset, Math.min(offset+limit, postsInformation.size()));
        map.put("count", l.size());
        map.put("posts", l);
        return map;
    }

    @GetMapping("/api/post/byTag/")
    public Map<String, Object> getApiPostByTag(@RequestParam("offset") int offset,
                                                @RequestParam("limit") int limit,
                                                @RequestParam("tag") String tag) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> postsInformation = new ArrayList<>();
        postRepository.getAvailablePostsByTag(tag).forEach(p -> postsInformation.add(p.getGeneralInformation(userRepository)));
        List<Map<String, Object>> l = postsInformation.subList(offset, Math.min(offset+limit, postsInformation.size()));
        map.put("count", l.size());
        map.put("posts", l);
        return map;
    }
}
