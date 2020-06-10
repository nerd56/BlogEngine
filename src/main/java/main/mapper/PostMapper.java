package main.mapper;

import main.dto.PostDto;
import main.dto.UserDto;
import main.model.Post;
import main.model.PostVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    @Autowired
    private UserMapper userMapper;

    public PostDto toDto(Post p) {
        PostDto dto = new PostDto(p.getId(), p.getViewCount(), p.getTitle(), p.getText(), p.getTime());
        UserDto user = userMapper.toDto(p.getUser());
        List<PostVote> votes = p.getPostVotes();
        int dislikeCount = 0;
        for (PostVote v : votes) if (v.getValue() == -1) dislikeCount++;
        int likeCount = votes.size()-dislikeCount;
        int commentCount = p.getPostComments().size();
        dto.setUser(user);
        dto.setLikeCount(likeCount);
        dto.setDislikeCount(dislikeCount);
        dto.setCommentCount(commentCount);
        return dto;
    }
}
