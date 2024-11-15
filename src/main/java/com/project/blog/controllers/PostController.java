package com.project.blog.controllers;

import com.project.blog.payloads.PostDto;
import com.project.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable(name = "userId") Integer uId, @PathVariable(name = "categoryId") Integer cId) {
        PostDto createdPost = this.postService.createPost(postDto, uId, cId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }
}