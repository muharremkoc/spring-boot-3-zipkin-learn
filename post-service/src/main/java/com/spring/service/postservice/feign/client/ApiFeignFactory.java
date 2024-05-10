package com.spring.service.postservice.feign.client;

import com.spring.service.postservice.feign.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ApiFeignFactory implements ApiFeignClient {

    final ApiFeignClient apiFeignClient;

    public ApiFeignFactory(@Qualifier("com.spring.service.postservice.feign.client.ApiFeignClient") ApiFeignClient apiFeignClient) {
        this.apiFeignClient = apiFeignClient;
    }

    @Override
    public User getUser(Integer userId) {
        return apiFeignClient.getUser(userId);
    }

}