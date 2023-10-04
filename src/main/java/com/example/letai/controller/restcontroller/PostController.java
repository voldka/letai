package com.example.letai.controller.restcontroller;

import com.example.letai.model.dto.PostDTO;
import com.example.letai.model.entity.PostEntity;
import com.example.letai.repository.PostRepository;
import com.example.letai.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.letai.exception.exceptionhandler.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postServices;
    @GetMapping("/list-post")
    public List<PostDTO> getAllPosts() {
        return postServices.findAll();
    }

    @PostMapping("/post")
    public PostDTO createPost(@RequestBody PostDTO post) {
        return postServices.save(post);
    }

    @GetMapping("/post/{id}")
    public PostDTO getPostById(@PathVariable(value = "id") Long postId) {
        return postServices.findById(postId);
    }

    @PutMapping("/post/{id}")
    public PostDTO updatePost(@PathVariable(value = "id") Long postId, @RequestBody PostDTO postDetails) {
        return postServices.updatePost(postId,postDetails);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "id") Long commentId) {
        postServices.deletePostById(commentId);
        return ResponseEntity.ok().build();
    }
}