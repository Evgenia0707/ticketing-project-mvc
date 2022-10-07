package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface CrudService <T, ID> {
    //all same only param differ use Generic

    T save(T role);//ret obj accept obj
    T findById(ID username);
    List<T> findAll();
    void deleteById(ID username);
    void update(T object);


}
