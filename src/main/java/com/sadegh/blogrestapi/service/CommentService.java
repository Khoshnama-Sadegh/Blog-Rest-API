package com.sadegh.blogrestapi.service;

import com.sadegh.blogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto findCommentByPostId(Long postId,Long commentId);

    CommentDto updateCommentById(Long postId,Long commentId,CommentDto commentDto);

    String deleteCommentById(Long postId,Long commentId);
}
