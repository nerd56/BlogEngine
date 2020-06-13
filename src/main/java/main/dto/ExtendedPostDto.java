package main.dto;

import org.springframework.stereotype.Component;

import java.util.List;

public class ExtendedPostDto extends PostDto {
    private List<String> tags;
    private List<CommentDto> comments;

    public ExtendedPostDto(PostDto p, List<String> tags, List<CommentDto> comments) {
        super(p.getId(), p.getUser(), p.getAnnounce(), p.getTime(), p.getTitle(), p.getCommentCount(), p.getLikeCount(), p.getDislikeCount(), p.getViewCount());
        this.tags = tags;
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
