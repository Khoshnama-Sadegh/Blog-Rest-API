package com.sadegh.blogrestapi.service.impl;

import com.sadegh.blogrestapi.entity.Comment;
import com.sadegh.blogrestapi.entity.Post;
import com.sadegh.blogrestapi.exception.BlogApiException;
import com.sadegh.blogrestapi.exception.ResourceNotFoundException;
import com.sadegh.blogrestapi.payload.CommentDto;
import com.sadegh.blogrestapi.payload.PostDto;
import com.sadegh.blogrestapi.repository.CommentRepository;
import com.sadegh.blogrestapi.repository.PostRepository;
import com.sadegh.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private ModelMapper mapper;
    private CommentRepository commentRepository;
    private PostRepository postRepository;



    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.mapper=mapper;
    }


    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Comment comment=toEntity(commentDto);

        Post post=postRepository.findById(postId).
                orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        comment.setPost(post);

        Comment commentResponse=commentRepository.save(comment);

        return toDto(commentResponse);
    }





    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comments by postId

        List<Comment> comments=commentRepository.findByPostId(postId);


        //convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto findCommentByPostId(Long postId, Long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comments Does not belong to this post");
        }else
            return toDto(comment);

    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {

        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comments Does not belong to this post");
        }


        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());


        Comment newComment=commentRepository.save(comment);


        return toDto(newComment);
    }

    @Override
    public String deleteCommentById(Long postId, Long commentId) {

        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comments Does not belong to this post");
        }

        commentRepository.delete(comment);

        return "the Comment with id "+comment.getId()+" has been deleted successfully";

    }


    //conversion of dto and entity

    private Comment toEntity(CommentDto commentDto){
        Comment comment=mapper.map(commentDto,Comment.class);
        return comment;
    }


    //conversion dto to entity

    private CommentDto toDto(Comment comment){

        CommentDto commentDto=mapper.map(comment,CommentDto.class);

        return commentDto;
    }
}
