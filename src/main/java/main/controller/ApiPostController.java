package main.controller;

import main.dto.PostDto;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getApiPost(@RequestParam("offset") int offset,
                                              @RequestParam("limit") int limit,
                                              @RequestParam("mode") String mode) {
        List<PostDto> l = postService.getPosts(offset, limit, mode);
        return new ResponseEntity<>(l, HttpStatus.OK);
    }
}
