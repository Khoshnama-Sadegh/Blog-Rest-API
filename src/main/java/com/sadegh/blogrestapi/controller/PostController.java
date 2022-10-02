package com.sadegh.blogrestapi.controller;

import com.sadegh.blogrestapi.payload.PostDto;
import com.sadegh.blogrestapi.payload.PostResponse;
import com.sadegh.blogrestapi.service.PostService;
import com.sadegh.blogrestapi.utils.AppConstants;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

    }


    @GetMapping("/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue =AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir){

        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }



    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long id){

        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }



    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") long id){

        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);


    }



    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") long id){

        return new ResponseEntity<>(postService.deletePost(id),HttpStatus.OK);


    }


}
