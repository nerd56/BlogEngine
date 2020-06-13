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
        UserDto user = userMapper.toDto(p.getUser());
        List<PostVote> votes = p.getPostVotes();
        int dislikeCount = (int) votes.stream().filter(v -> v.getValue() == -1).count();
        int likeCount = votes.size()-dislikeCount;
        int commentCount = p.getPostComments().size();
        PostDto dto = new PostDto(p.getId(), user, p.getText(), p.getTime(), p.getTitle(), commentCount, likeCount, dislikeCount, p.getViewCount());
        return dto;
    }
}
