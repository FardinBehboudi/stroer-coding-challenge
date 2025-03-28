package com.fardin.stroer.controller;

import com.fardin.stroer.dto.UserWithPosts;
import com.fardin.stroer.model.Post;
import com.fardin.stroer.model.User;
import com.fardin.stroer.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        reset(userService);
    }

    @Test
    public void testGetUserWithPosts(){
        // Prepare test data
        User user = new User();
        user.setId(1);
        user.setName("Stroer User");

        Post post = new Post();
        post.setId(1);
        post.setTitle("Stroer Post");

        UserWithPosts userWithPosts = new UserWithPosts();
        userWithPosts.setUser(user);
        userWithPosts.setPosts(List.of(post));

        // Mock the service to return user with posts
        when(userService.getUserWithPosts(1)).thenReturn(Mono.just(userWithPosts));

        // perform API test
        webTestClient.get()
                .uri("/api/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.user.name").isEqualTo("Stroer User")
                .jsonPath("$.posts.[0].title").isEqualTo("Stroer Post");
    }

    @Test
    public void testUserNotFound() {
        // Mock the service to throw an exception
        when(userService.getUserWithPosts(999)).thenReturn(Mono.error(new RuntimeException("User not found")));

        // Perform API test
        webTestClient.get()
                .uri("/api/users/999")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("User not found")
                .jsonPath("$.status").isEqualTo(400);
    }
}