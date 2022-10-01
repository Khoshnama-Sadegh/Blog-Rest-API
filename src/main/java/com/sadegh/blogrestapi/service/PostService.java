package com.sadegh.blogrestapi.service;

import com.sadegh.blogrestapi.entity.Post;
import com.sadegh.blogrestapi.payload.PostDto;
import com.sadegh.blogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    String deletePost(long id);
}
