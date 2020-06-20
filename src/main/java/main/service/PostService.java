package main.service;

import main.dto.CountListResponse;
import main.dto.ExtendedPostDto;
import main.dto.PostDto;
import main.mapper.ExtendedPostMapper;
import main.mapper.PostMapper;
import main.model.Post;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ExtendedPostMapper extendedPostMapper;

    @Autowired
    private PostRepository postRepository;

    public CountListResponse getPosts(int offset, int limit, String sort) {
        int page = offset / limit;
        List<Post> posts;
        switch (sort) {
            case "best":
                posts = postRepository.getAvailablePostsSortedByLikeCount(PageRequest.of(page, limit));
                break;
            case "popular":
                posts = postRepository.getAvailablePostsSortedByCommentsCount(PageRequest.of(page, limit));
                break;
            case "recent":
                posts = postRepository.getAvailablePosts(PageRequest.of(page, limit, Sort.by("time").descending()));
                break;
            case "early":
                posts = postRepository.getAvailablePosts(PageRequest.of(page, limit, Sort.by("time")));
                break;
            default:
                return getPosts(offset, limit);
        }
        int count = postRepository.getAvailablePostsCount();
        return new CountListResponse(count, posts.stream().map(postMapper::toDto).collect(Collectors.toList()));
    }

    public CountListResponse getPostsSearch(int offset, int limit, String query) {
        query = "%"+query+"%";
        int page = offset/limit;
        int count = postRepository.getAvailablePostsByQueryCount(query);
        List<PostDto> posts = postRepository.getAvailablePostsByQuery(query, PageRequest.of(page, limit)).stream().map(postMapper::toDto).collect(Collectors.toList());
        return new CountListResponse(count, posts);
    }

    public Optional<ExtendedPostDto> getPost(int id) {
        Optional<Post> optionalPost = Optional.ofNullable(postRepository.findAvailablePost(id));
        if (!optionalPost.isPresent()) {
            return Optional.empty();
        }

        Post post = optionalPost.get();
        ExtendedPostDto extendedPostDto = extendedPostMapper.toDto(post);

        post.setViewCount(post.getViewCount()+1);
        postRepository.save(post);
        return Optional.of(extendedPostDto);
    }

    public Optional<CountListResponse> getPostsByDate(int offset, int limit, String date) {
        int page = offset / limit;
        try {
            LocalDate localDate = LocalDate.parse(date, dtf);
            List<PostDto> posts = postRepository.getAvailablePostsByDate(localDate, PageRequest.of(page, limit))
                    .stream().map(postMapper::toDto).collect(Collectors.toList());
            int count = postRepository.getAvailablePostsByDateCount(localDate);
            CountListResponse response = new CountListResponse(count, posts);
            return Optional.of(response);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public CountListResponse getPostsByTag(int offset, int limit, String tag) {
        int page = offset / limit;
        List<Post> posts = postRepository.getAvailablePostsByTag(tag, PageRequest.of(page, limit));
        int count = postRepository.getAvailablePostsByTagCount(tag);
        return new CountListResponse(count, posts.stream().map(postMapper::toDto).collect(Collectors.toList()));
    }

    private CountListResponse getPosts(int offset, int limit) {
        int page = offset / limit;
        List<PostDto> posts = postRepository.getAvailablePosts(PageRequest.of(page, limit)).stream().map(postMapper::toDto).collect(Collectors.toList());
        int count = postRepository.getAvailablePostsCount();
        CountListResponse response = new CountListResponse(count, posts);
        return response;
    }
}
