package main.model;

import main.repository.UserRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "post_comments")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "post_id", nullable = false)
    private int postId;
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false, name = "text", columnDefinition = "TEXT")
    private String text;

    public Map<String, Object> getGeneralInformation(UserRepository userRepository) {
        User user = userRepository.findById(userId).get();
        Map<String, Object> map = new HashMap<String, Object>(){{
            put("id", id);
            put("time", time);
            put("text", text);
            put("user", new HashMap<String, Object>(){{put("id", user.getId()); put("name", user.getName()); put("photo", user.getPhoto());}});
        }};
        return map;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
