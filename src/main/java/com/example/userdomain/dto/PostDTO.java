package com.example.userdomain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private String title;
    private String content;
    private boolean pinned;

}
