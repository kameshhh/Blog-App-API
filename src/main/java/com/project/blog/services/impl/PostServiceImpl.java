package com.project.blog.services.impl;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostDto;
import com.project.blog.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public Post createPost(PostDto postDto) {
        return null;
    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<Post> getAllPosts() {
        return List.of();
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<Post> getPostByCategory(Integer categoryId) {
        return List.of();
    }

    @Override
    public List<Post> getPostByUser(Integer userId) {
        return List.of();
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return List.of();
    }
}
