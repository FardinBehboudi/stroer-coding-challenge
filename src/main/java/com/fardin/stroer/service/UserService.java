package com.fardin.stroer.service;

import com.fardin.stroer.dto.UserWithPosts;
import com.fardin.stroer.model.Post;
import com.fardin.stroer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

    public Mono<UserWithPosts> getUserWithPosts(int userId){

        Mono<User> userMono = webClient
                .get()
                .uri("/users/{id}",userId)
                .retrieve()
                .bodyToMono(User.class);

        Mono<List<Post>> postsMono = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("Posts")
                        .queryParam("userId",userId)
                        .build())
                .retrieve()
                .bodyToFlux(Post.class)
                .collectList();

        return Mono.zip(userMono,postsMono,(user,post) ->{
            UserWithPosts userWithPosts = new UserWithPosts();
            userWithPosts.setUser(user);
            userWithPosts.setPosts(post);
            return userWithPosts;
        });
    }
}
