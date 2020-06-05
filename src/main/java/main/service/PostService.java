package main.service;

import main.dto.PostDto;
import main.mapper.PostMapper;
import main.model.Post;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepository postRepository;

    public List<PostDto> getPosts(int offset, int limit, String sort) {
        List<PostDto> l = new ArrayList<>();
        List<Post> posts;
        switch (sort) {
            case "best":
                posts = postRepository.getAvailablePostsSortedByLikeCount(OffsetLimitPageable.of(offset, limit));
                break;
            case "popular":
                posts = postRepository.getAvailablePostsSortedByCommentsCount(OffsetLimitPageable.of(offset, limit));
                break;
            case "recent":
                posts = postRepository.getAvailablePosts(OffsetLimitPageable.of(offset, limit, Sort.by("time").descending()));
                break;
            case "early":
                posts = postRepository.getAvailablePosts(OffsetLimitPageable.of(offset, limit, Sort.by("time")));
                break;
            default:
                posts = postRepository.getAvailablePosts();
                break;
        }
        posts.forEach(p -> l.add(postMapper.toDto(p)));
        return l;
    }
}
