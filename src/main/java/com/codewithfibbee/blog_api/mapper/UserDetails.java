package com.codewithfibbee.blog_api.mapper;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDetails {
    @NotNull(message = "UserName cannot be Null")
    private String userName;

    @NotNull(message = "Email cannot be Null")
    private String email;

    @NotNull(message = "Password cannot be Null")
    @Size(min = 4, max = 12, message = "Password must be equal or greater than 4 characters or less than 12 characters")
    private String password;
}
