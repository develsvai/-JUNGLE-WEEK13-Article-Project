package com.example.userdomain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDTO {

    private Long id;
    private String title;
    private String content;
    private boolean pinned;

}
