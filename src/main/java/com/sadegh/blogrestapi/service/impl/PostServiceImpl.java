package com.sadegh.blogrestapi.service.impl;

import com.sadegh.blogrestapi.entity.Post;
import com.sadegh.blogrestapi.exception.ResourceNotFoundException;
import com.sadegh.blogrestapi.payload.PostDto;
import com.sadegh.blogrestapi.payload.PostResponse;
import com.sadegh.blogrestapi.repository.PostRepository;
import com.sadegh.blogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper mapper;

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity

       Post post=toEntity(postDto);

       Post postResponse= postRepository.save(post);

        //convert entity to dto
        PostDto postDtoResponse= toDto(postResponse);


        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();


        //create  Pageable instance

        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts= postRepository.findAll(pageable);

        List<Post> listOfPosts=posts.getContent();

        List<PostDto> postDtos= listOfPosts.stream().map(post -> toDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());



        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {

        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","Id",id));

        PostDto postDto=toDto(post);


        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","Id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost=postRepository.save(post);


        return toDto(newPost);
    }

    @Override
    public String deletePost(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));

        postRepository.deleteById(id);


        return "Post with id "+ id + " has been deleted";
    }







    private Post toEntity(PostDto postDto){
        Post post=mapper.map(postDto,Post.class);

        return post;
    }


    private PostDto toDto(Post post){

        PostDto postDto=mapper.map(post,PostDto.class);


        return postDto;
    }
}
