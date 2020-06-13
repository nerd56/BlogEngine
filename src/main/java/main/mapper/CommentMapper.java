package main.mapper;

import main.dto.CommentDto;
import main.dto.ExtendedUserDto;
import main.model.PostComment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(PostComment c, ExtendedUserDto userDto) {
        CommentDto commentDto = new CommentDto(c.getId(), c.getTime(), c.getText(), userDto);
        return commentDto;
    }
}
