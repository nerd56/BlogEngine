package main.controller;

import main.dto.CountListResponse;
import main.dto.ExtendedPostDto;
import main.repository.PostRepository;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public ResponseEntity<CountListResponse> getApiPost(@RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("mode") String mode) {
        CountListResponse response = postService.getPosts(offset, limit, mode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<CountListResponse> getApiPostSearch(@RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("query") String query) {
        Optional<CountListResponse> response = postService.getPostsSearch(offset, limit, query);
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(response.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity getApiPostById(@PathVariable("id") int id) {
        Optional<ExtendedPostDto> post = postService.getPost(id);
        if (post.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(post.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/byDate")
    public ResponseEntity<CountListResponse> getApiPostByDate(@RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("date") String date) {
        Optional<CountListResponse> response = postService.getPostsByDate(offset, limit, date);
        if (response.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(response.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/byTag")
    public ResponseEntity<CountListResponse> getApiPostByTag(@RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("tag") String tag) {
        CountListResponse response = postService.getPostsByTag(offset, limit, tag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
