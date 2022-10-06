package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {  //"2" //what convert to what

    RoleService roleService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {//      Str"2"

        if (source == null || source.equals("")){
            return null;
        }
        return roleService.findById(Long.parseLong(source));// return 2L

        //roleService.findById(source)
    }
}

/*
purpose of converter
    What dropdown returns to the backend?
    It is returning id, but userDTO doesn't accept role id, it accepts roleDTO object inside of it.
    So we need to take the id from html and find the roleDTO based on id information, and save that roleDTO with the new user/userDTO

 */



