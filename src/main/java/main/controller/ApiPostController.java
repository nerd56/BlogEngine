package main.controller;

import main.dto.PostDto;
import main.repository.PostRepository;
import main.response.CountListResponse;
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
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public ResponseEntity<CountListResponse> getApiPost(@RequestParam("offset") int offset,
                                                        @RequestParam("limit") int limit,
                                                        @RequestParam("mode") String mode) {
        int count = postRepository.getAvailablePostsCount();
        List<PostDto> l = postService.getPosts(offset, limit, mode);
        return new ResponseEntity<>(new CountListResponse(count, l), HttpStatus.OK);
    }
}
