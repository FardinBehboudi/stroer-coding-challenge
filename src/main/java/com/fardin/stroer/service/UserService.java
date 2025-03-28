package com.fardin.stroer.service;

import com.fardin.stroer.dto.UserWithPosts;
import com.fardin.stroer.model.Post;
import com.fardin.stroer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service to fetch user data and posts asynchronously using WebClient.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

    public Mono<UserWithPosts> getUserWithPosts(int userId){

        Mono<User> userMono = webClient
                .get()
                .uri("/users/{id}",userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("User not found")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Server error while fetching user")))
                .bodyToMono(User.class);

        Mono<List<Post>> postsMono = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("Posts")
                        .queryParam("userId",userId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Posts not found")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Server error while fetching posts")))

                .bodyToFlux(Post.class)
                .collectList();

        return Mono.zip(userMono,postsMono,(user,post) ->{
            UserWithPosts userWithPosts = new UserWithPosts();
            userWithPosts.setUser(user);
            userWithPosts.setPosts(post);
            return userWithPosts;
        }).onErrorResume(e->Mono.error(new RuntimeException("Error fetching user or posts: " + e.getMessage())));
    }
}
