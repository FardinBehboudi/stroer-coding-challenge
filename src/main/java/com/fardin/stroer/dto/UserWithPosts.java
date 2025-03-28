package com.fardin.stroer.dto;

import com.fardin.stroer.model.Post;
import com.fardin.stroer.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserWithPosts {
    private User user;
    private List<Post> posts;
}
