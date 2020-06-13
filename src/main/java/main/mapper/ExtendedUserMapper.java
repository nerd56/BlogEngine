package main.mapper;

import main.dto.ExtendedUserDto;
import main.dto.UserDto;
import main.model.User;
import org.springframework.stereotype.Component;

@Component
public class ExtendedUserMapper extends UserMapper {
    public ExtendedUserDto toDto(User u) {
        UserDto userDto = super.toDto(u);
        ExtendedUserDto extendedUserDto = new ExtendedUserDto(userDto, u.getPhoto());
        return extendedUserDto;
    }
}
