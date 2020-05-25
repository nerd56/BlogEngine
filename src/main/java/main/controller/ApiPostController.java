package main.controller;


import main.model.ModerationStatus;
import main.model.Post;
import main.model.PostRepository;
import main.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RestController
public class ApiPostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/api/post/{offset}&{limit}&{mode}")
    public HashMap<String, Object> getApiPost(@PathVariable("offset") int offset,
                                              @PathVariable("limit") int limit,
                                              @PathVariable("mode") String mode) {
        HashMap<String, Object> map = new HashMap<>();
        List<Post> allPosts = new ArrayList<>();
        postRepository.findAll().forEach(allPosts::add);

        int count = allPosts.size();
        limit = Math.min(count, limit);

        if (mode.equals("recent")) allPosts.sort(Comparator.comparing(Post::getTime));
        else if (mode.equals("best")) allPosts.sort(Comparator.comparing(Post::getLikeCount));
        else if (mode.equals("popular")) allPosts.sort(Comparator.comparing(Post::getCommentCount));
        else if (mode.equals("early")) allPosts.sort(Comparator.comparing(Post::getTime).reversed());

        List<HashMap<String, Object>> posts = new ArrayList<>();
        for (int i = count-1, c = 0; i >= 0 && c < limit; i--) {
            Post post = allPosts.get(i);
            if (post.isActive() && post.getTime().compareTo(LocalDateTime.now()) <= 0 && post.getModerationStatus() == ModerationStatus.ACCEPTED) {
                HashMap<String, Object> postMap = new HashMap<String, Object>(){
                    {
                        put("id", post.getId());
                        put("time", post.getTime());
                        put("user", new HashMap<String, Object>(){{put("id", post.getUserId()); put("name", userRepository.findById(post.getUserId()).get().getName());}});
                        put("title", post.getTitle());
                        put("announce", post.getText());
                        put("likeCount", post.getLikeCount());
                        put("dislikeCount", post.getDislikeCount());
                        put("commentCount", post.getCommentCount());
                        put("viewCount", post.getViewCount());
                    }
                };
                posts.add(postMap);
                c++;
            }
        }

        map.put("count", count);
        map.put("posts", posts);
        return map;
    }
}
