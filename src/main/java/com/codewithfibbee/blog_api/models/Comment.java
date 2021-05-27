package com.codewithfibbee.blog_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Post post;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> like;
}
