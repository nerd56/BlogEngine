package main.mapper;

import main.dto.CommentDto;
import main.dto.ExtendedPostDto;
import main.dto.PostDto;
import main.model.Post;
import main.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExtendedPostMapper extends PostMapper {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ExtendedUserMapper extendedUserMapper;

    @Override
    public ExtendedPostDto toDto(Post p) {
        PostDto postDto = super.toDto(p);
        List<CommentDto> commentDtoList = p.getPostComments().stream().map(c -> commentMapper.toDto(c, extendedUserMapper.toDto(c.getUser()))).collect(Collectors.toList());
        List<String> tags = p.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        ExtendedPostDto extendedPostDto = new ExtendedPostDto(postDto, tags, commentDtoList);
        return extendedPostDto;
    }
}
