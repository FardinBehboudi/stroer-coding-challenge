package com.fardin.stroer.controller;

import com.fardin.stroer.dto.UserWithPosts;
import com.fardin.stroer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * REST Controller to expose the User with Posts API.
 *
 * <p>To test this endpoint, send a GET request to:
 * <pre>
 *     GET /api/users/{id}
 *     Example: /api/users/1
 * </pre>
 *
 * This will return the user's information along with all posts created by that user.
 */
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
