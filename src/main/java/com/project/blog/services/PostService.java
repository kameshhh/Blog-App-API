package com.project.blog.services;

import com.project.blog.entities.Post;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostByCategory(Integer categoryId);
    List<PostDto> getPostByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
}
