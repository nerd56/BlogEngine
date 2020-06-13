package main.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private int id;
    private LocalDateTime time;
    private String text;
    private ExtendedUserDto user;

    public CommentDto(int id, LocalDateTime time, String text, ExtendedUserDto user) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ExtendedUserDto getUser() {
        return user;
    }

    public void setUser(ExtendedUserDto user) {
        this.user = user;
    }
}
