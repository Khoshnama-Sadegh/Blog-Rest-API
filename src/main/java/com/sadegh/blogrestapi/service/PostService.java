package com.sadegh.blogrestapi.service;

import com.sadegh.blogrestapi.entity.Post;
import com.sadegh.blogrestapi.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    String deletePost(long id);
}
