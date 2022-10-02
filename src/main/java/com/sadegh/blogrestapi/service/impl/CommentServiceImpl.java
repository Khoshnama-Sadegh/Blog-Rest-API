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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
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
            throw new BlogApiException("Comments Does not belong to this post",HttpStatus.BAD_REQUEST);
        }else
            return toDto(comment);

    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {

        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comments Does not belong to this post",HttpStatus.BAD_REQUEST);
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
            throw new BlogApiException("Comments Does not belong to this post",HttpStatus.BAD_REQUEST);
        }

        commentRepository.delete(comment);

        return "the Comment with id "+comment.getId()+" has been deleted successfully";

    }


    //conversion of dto and entity

    private Comment toEntity(CommentDto commentDto){
        Comment comment=new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }


    //conversion dto to entity

    private CommentDto toDto(Comment comment){

        CommentDto commentDto=new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }
}
