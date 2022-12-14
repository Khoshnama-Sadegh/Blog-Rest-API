package com.sadegh.blogrestapi.controller;

import com.sadegh.blogrestapi.payload.CommentDto;
import com.sadegh.blogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@Valid  @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);


    }



    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(value = "postId") long postId){

        return commentService.getCommentsByPostId(postId);
    }



    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>getCommentByPostId(@PathVariable(value = "postId") long postId,
                                                        @PathVariable(value = "commentId")long commentId){

        return new ResponseEntity<>(commentService.findCommentByPostId(postId,commentId),HttpStatus.OK);

    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>updateCommentById(@PathVariable("postId") long postId,
                                                       @PathVariable("commentId") long commentId,
                                                       @Valid @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto),HttpStatus.OK);
    }



    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String>deleteCommentById(@PathVariable("postId") long postId,
                                                   @PathVariable("commentId")long commentId){

        return new ResponseEntity<>(commentService.deleteCommentById(postId,commentId),HttpStatus.OK);


    }

}
