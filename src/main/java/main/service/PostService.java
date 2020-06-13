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

    @Autowired
    private EntityManager entityManager;

    public CountListResponse getPosts(int offset, int limit, String sort) {
        int page = offset / limit;
        List<Post> posts;
        if (sort.equals("best"))
            posts = postRepository.getAvailablePostsSortedByLikeCount(PageRequest.of(offset, limit));
        else if (sort.equals("popular"))
            posts = postRepository.getAvailablePostsSortedByCommentsCount(PageRequest.of(offset, limit));
        else if (sort.equals("recent"))
            posts = postRepository.getAvailablePosts(PageRequest.of(offset, limit, Sort.by("time").descending()));
        else if (sort.equals("early"))
            posts = postRepository.getAvailablePosts(PageRequest.of(offset, limit, Sort.by("time")));
        else
            return getPosts(offset, limit);
        int count = postRepository.getAvailablePostsCount();
        return new CountListResponse(count, posts.stream().map(postMapper::toDto).collect(Collectors.toList()));
    }

    public Optional<CountListResponse> getPostsSearch(int offset, int limit, String query) {
        if (query.isEmpty()) return Optional.of(getPosts(offset, limit));
        String selectPosts = "SELECT * FROM (" + query + ") as posts WHERE " + PostRepository.postIsAvailable;
        String selectPostsCount = "SELECT COUNT(*) FROM (" + query + ") as posts WHERE " + PostRepository.postIsAvailable;

        try {
            int page = offset / limit;
            Query q = entityManager.createNativeQuery(selectPosts, Post.class);
            q.setFirstResult(page * limit);
            q.setMaxResults(limit);
            List<PostDto> posts = (List<PostDto>) q.getResultList().stream().map(p -> postMapper.toDto((Post) p)).collect(Collectors.toList());
            int count = ((BigInteger) entityManager.createNativeQuery(selectPostsCount).setMaxResults(1).getResultList().get(0)).intValue();
            CountListResponse response = new CountListResponse(count, posts);
            return Optional.of(response);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(selectPosts);
            return Optional.empty();
        }
    }

    public Optional<ExtendedPostDto> getPost(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return Optional.empty();
        }

        Post post = optionalPost.get();

        ExtendedPostDto extendedPostDto = extendedPostMapper.toDto(post);
        return Optional.of(extendedPostDto);
    }

    public Optional<CountListResponse> getPostsByDate(int offset, int limit, String date) {
        int page = offset / limit;
        try {
            LocalDate localDate = LocalDate.parse(date, dtf);
            List<PostDto> posts = postRepository.getAvailablePosts(localDate, PageRequest.of(page, limit))
                    .stream().map(postMapper::toDto).collect(Collectors.toList());
            int count = postRepository.getAvailablePostsCount(localDate);
            CountListResponse response = new CountListResponse(count, posts);
            return Optional.of(response);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public CountListResponse getPostsByTag(int offset, int limit, String tag) {
        int page = offset / limit;
        List<Post> posts = postRepository.getAvailablePosts(tag, PageRequest.of(page, limit));
        int count = postRepository.getAvailablePostsCount(tag);
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
