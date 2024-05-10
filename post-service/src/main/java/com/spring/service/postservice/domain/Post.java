package com.spring.service.postservice.domain;

import com.spring.service.postservice.enums.Platform;
import com.spring.service.postservice.model.PostDto;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "posts")
@SuperBuilder
public class Post extends PostAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private int userId;

    public Post() {
    }

    public Post(int postId, Platform platform, int userId) {
        this.postId = postId;
        this.platform = platform;
        this.userId = userId;
    }

    public int getId() {
        return postId;
    }

    public void setId(int id) {
        this.postId = id;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
