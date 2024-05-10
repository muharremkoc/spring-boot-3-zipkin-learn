package com.spring.service.postservice.model;

import com.spring.service.postservice.domain.Post;
import com.spring.service.postservice.feign.User;

import java.util.List;

public class PostDto {


    private User user;

    private Post posts;

    public PostDto() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPosts() {
        return posts;
    }

    public void setPosts(Post posts) {
        this.posts = posts;
    }
}
