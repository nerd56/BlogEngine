package main.mapper;

import main.dto.PostDto;
import main.dto.UserDto;
import main.model.Post;
import main.repository.PostRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public PostDto toDto(Post p) {
        PostDto dto = new PostDto(p.getId(), p.getViewCount(), p.getTitle(), p.getText(), p.getTime());
        UserDto user = userMapper.toDto(userRepository.findById(p.getUserId()).get());
        int likeCount = postRepository.getLikeCount(dto.getId());
        int dislikeCount = postRepository.getDislikeCount(dto.getId());
        int commentCount = postRepository.getCommentCount(dto.getId());
        dto.setUser(user);
        dto.setLikeCount(likeCount);
        dto.setDislikeCount(dislikeCount);
        dto.setCommentCount(commentCount);
        return dto;
    }
}
