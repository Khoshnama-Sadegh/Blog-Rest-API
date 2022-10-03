package com.sadegh.blogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
public class PostDto {
    private Long id;

    private String title;

    private String description;

    private String content;

    private Set<CommentDto> comments;


}
