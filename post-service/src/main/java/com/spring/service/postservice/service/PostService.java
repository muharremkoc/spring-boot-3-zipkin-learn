package com.spring.service.postservice.service;

import com.spring.service.postservice.domain.Post;
import com.spring.service.postservice.enums.Platform;
import com.spring.service.postservice.exceptions.PostNotFoundException;
import com.spring.service.postservice.exceptions.UserNotFoundException;
import com.spring.service.postservice.model.PostDto;

import java.util.List;

public interface PostService {

    Post createPost(Platform platform,int userId) throws UserNotFoundException;


    List<PostDto> getPosts();

    Post getPostByPostId(int id) throws PostNotFoundException;
}
