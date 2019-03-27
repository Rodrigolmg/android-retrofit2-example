package com.fatesg.senaigo.retrofit2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPost {

    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
