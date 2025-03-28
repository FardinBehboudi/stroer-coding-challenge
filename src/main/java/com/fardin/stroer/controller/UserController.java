package com.fardin.stroer.controller;

import com.fardin.stroer.dto.UserWithPosts;
import com.fardin.stroer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<UserWithPosts> getUserWithPosts(@PathVariable int id) {
        return userService.getUserWithPosts(id);

    }
}
