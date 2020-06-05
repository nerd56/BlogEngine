package main.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

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
