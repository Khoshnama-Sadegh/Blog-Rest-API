package com.sadegh.blogrestapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts"
        ,uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "content",nullable = false)
    private String content;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();
}
