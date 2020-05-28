package main.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "moderation_status", columnDefinition = "ENUM('ACCEPTED', 'DECLINED', 'NEW')")
    private ModerationStatus moderationStatus;
    @Column(name = "moderator_id")
    private int moderatorId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(name = "view_count", nullable = false)
    private int viewCount;

    public List<PostComment> getComments(PostCommentRepository postCommentRepository) {
        return postCommentRepository.getCommentsByPostId(id);
    }

    public List<Tag> getTags(TagRepository tagRepository) {
        String sql = "select tag_id from tag2post where post_id = " + id;
        List<Tag> tags = new ArrayList<>();
        List<Integer> tagsId = new ArrayList<>();
        try (Statement s = SQLConnection.getConnection().createStatement()) {
            ResultSet r = s.executeQuery(sql);
            while (r.next()) tagsId.add(r.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
        tagRepository.findAllById(tagsId).forEach(tags::add);
        return tags;
    }

    public Map<String, Object> getGeneralInformation(UserRepository userRepository) {
        Map<String, Object> map = new HashMap<String, Object>(){
            {
                put("id", getId());
                put("time", getTime());
                put("user", new HashMap<String, Object>(){{
                    put("id", getUserId());
                    put("name", userRepository.findById(getUserId()).get().getName());
                }});
                put("title", getTitle());
                put("announce", getText());
                put("likeCount", getLikeCount());
                put("dislikeCount", getDislikeCount());
                put("commentCount", getCommentCount());
                put("viewCount", getViewCount());
            }
        };
        return map;
    }

    public Map<String, Object> getDetailedInformation(UserRepository userRepository, PostCommentRepository postCommentRepository, TagRepository tagRepository) {
        Map<String, Object> map = getGeneralInformation(userRepository);
        List<Map<String, Object>> commentsInformation = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        getComments(postCommentRepository).forEach(c -> commentsInformation.add(c.getGeneralInformation(userRepository)));
        getTags(tagRepository).forEach(t -> tags.add(t.getName()));

        map.put("comments", commentsInformation);
        map.put("tags", tags);
        return map;
    }

    public int getLikeCount() { return Integer.parseInt(SQLConnection.getColumn("select count(*) from post_votes where post_id = " + id + " and post_votes.value = 1")); }
    public int getDislikeCount() { return Integer.parseInt(SQLConnection.getColumn("select count(*) from post_votes where post_id = " + id + " and post_votes.value = -1")); }
    public int getCommentCount() { return Integer.parseInt(SQLConnection.getColumn("select count(*) from post_comments where post_id = " + id)); }
    public boolean isAvailable() { return isActive() && getTime().compareTo(LocalDateTime.now(ZoneId.of("Europe/Moscow"))) <= 0 && getModerationStatus() == ModerationStatus.ACCEPTED; }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }
    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }
    public int getModeratorId() {
        return moderatorId;
    }
    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getViewCount() {
        return viewCount;
    }
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
