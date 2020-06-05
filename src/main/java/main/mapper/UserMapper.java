package main.mapper;

import main.dto.UserDto;
import main.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User u) {
        UserDto dto = new UserDto(u.getId(), u.getName());
        return dto;
    }
}
