package com.spring.service.postservice.controller;

import com.spring.service.postservice.domain.Post;
import com.spring.service.postservice.enums.Platform;
import com.spring.service.postservice.exceptions.PostNotFoundException;
import com.spring.service.postservice.exceptions.UserNotFoundException;
import com.spring.service.postservice.model.PostDto;
import com.spring.service.postservice.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestParam Platform platform,@RequestParam int userId) throws UserNotFoundException {
        return postService.createPost(platform, userId);
    }

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getPosts();
    }

    @GetMapping("{id}")
    public Post getById(@PathVariable int id) throws PostNotFoundException {
        return postService.getPostByPostId(id);
    }
}
