package com.codewithfibbee.blog_api.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> like;
}
