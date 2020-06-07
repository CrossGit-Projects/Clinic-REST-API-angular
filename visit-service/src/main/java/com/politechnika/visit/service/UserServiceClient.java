package com.politechnika.visit.service;

import com.politechnika.visit.model.custom.AppUser;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="users-service")
public interface UserServiceClient {

    @GetMapping("/users")
    List<AppUser> getUsers(@RequestParam String email);

    @GetMapping("/users/{appUserId}")
    @Headers("Content-Type: application/json")
    AppUser getUserById(@PathVariable Long appUserId);

}
