package com.spring.service.postservice.service;

import com.spring.service.postservice.domain.Post;
import com.spring.service.postservice.enums.Platform;
import com.spring.service.postservice.exceptions.PostNotFoundException;
import com.spring.service.postservice.exceptions.UserNotFoundException;
import com.spring.service.postservice.feign.User;
import com.spring.service.postservice.feign.client.ApiFeignClient;
import com.spring.service.postservice.model.PostDto;
import com.spring.service.postservice.repository.PostRepository;
import io.micrometer.tracing.annotation.NewSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class PostServiceImpl implements PostService{

    Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final ApiFeignClient apiFeignClient;

    public PostServiceImpl(PostRepository postRepository, @Qualifier("apiFeignFactory") ApiFeignClient apiFeignClient) {
        this.postRepository = postRepository;
        this.apiFeignClient = apiFeignClient;
    }

    @Override
    @NewSpan("post-service-create-post")
    public Post createPost(Platform platform, int userId) throws UserNotFoundException {
        User user=apiFeignClient.getUser(userId);
        if (user==null){
            log.info("User Not Found with ID:{}", userId);
            throw new UserNotFoundException("Wallet Not Found with ID:"+userId);
        }
        else {
            Post post=new Post();
            post.setPlatform(platform);
            post.setUserId(userId);
           return postRepository.save(post);
        }
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        posts.forEach(post -> {
            User user = apiFeignClient.getUser(post.getUserId());
            if (user == null) {
                log.info("User Not Found with ID: {}", post.getUserId());
                throw new RuntimeException("User Not Found with ID: " + post.getUserId());
            }
            PostDto postDto = new PostDto();
            postDto.setUser(user);
            postDto.setPosts(post);
            postDtos.add(postDto);
        });
        log.info("Get Posts Successfully");

        return postDtos;
    }

    @Override
    public Post getPostByPostId(int id) throws PostNotFoundException {
        Post post=postRepository.findByPostId(id);
        if (post==null){
            log.info("Post Not Found with ID:{}", id);
            throw new PostNotFoundException("Post Not Found with ID:"+id);
        }
        return post;
    }
}
