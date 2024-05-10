package com.spring.service.postservice.feign.client;

import com.spring.service.postservice.feign.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", url = "http://localhost:8089/user")
public interface ApiFeignClient {


    @GetMapping("/{id}")
    User getUser (@PathVariable("id") Integer userId);

}