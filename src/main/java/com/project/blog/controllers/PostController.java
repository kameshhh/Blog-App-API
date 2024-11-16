package com.project.blog.controllers;

import com.project.blog.payloads.PostDto;
import com.project.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable(name = "userId") Integer uId,
            @PathVariable(name = "categoryId") Integer cId
    )
    {
        PostDto createdPost = this.postService.createPost(postDto, uId, cId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> allPostsByUserId(
            @PathVariable(name = "userId") Integer uId
    )
    {
        List<PostDto> allPosts = this.postService.getPostByUser(uId);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> allPostsByCategoryId(
            @PathVariable(name = "categoryId") Integer cId
    )
    {
        List<PostDto> posts = this.postService.getPostByCategory(cId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}