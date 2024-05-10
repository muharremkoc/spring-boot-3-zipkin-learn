package com.spring.service.postservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public abstract class PostAudit implements Serializable {


    @CreatedDate
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    Date createdAt;

    public PostAudit(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PostAudit() {
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}