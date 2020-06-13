package main.dto;

import org.springframework.stereotype.Component;

public class ExtendedUserDto extends UserDto {
    private String photo;

    public ExtendedUserDto(UserDto u, String photo) {
        super(u.getId(), u.getName());
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
