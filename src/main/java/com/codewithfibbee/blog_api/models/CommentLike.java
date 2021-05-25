package com.codewithfibbee.blog_api.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
public class CommentLike {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User user;

}
