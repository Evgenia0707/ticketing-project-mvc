package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    // id 1 : admin 2:manager 3:employee etc....
    //every user has a Role

    private Long id;
    private String description;
}
