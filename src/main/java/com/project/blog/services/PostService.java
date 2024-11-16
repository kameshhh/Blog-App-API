package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    Post updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    List<Post> getAllPosts();
    Post getPostById(Integer postId);
    List<Post> getPostByCategory(Integer categoryId);
    List<Post> getPostByUser(Integer userId);
    List<Post> searchPosts(String keyword);

}
