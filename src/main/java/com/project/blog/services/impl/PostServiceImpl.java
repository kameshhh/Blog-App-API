package com.project.blog.services.impl;

import com.project.blog.entities.Category;
import com.project.blog.entities.Post;
import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.repo.CategoryRepo;
import com.project.blog.repo.PostRepo;
import com.project.blog.repo.UserRepo;
import com.project.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = this.postDtoPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRepo.save(post);
        return this.postToPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post savedPost = this.postRepo.save(post);
        return this.postToPostDto(savedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePosts = this.postRepo.findAll(p);

        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(this::postToPostDto).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        return this.postToPostDto(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post)).toList();

        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Post> posts = this.postRepo.findByUser(user);

        return posts.stream().map(post -> this.postToPostDto(post)).toList();
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map(post -> this.postToPostDto(post)).toList();
    }

    public Post postDtoPost(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }

    public  PostDto postToPostDto(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }

}
