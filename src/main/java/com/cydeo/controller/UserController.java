package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RoleService roleService;
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    //CREATE
    @GetMapping("/create")// UserCreate Page
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());//new UserDTO())
        model.addAttribute("roles", roleService.findAll());//find all roles from DB
        model.addAttribute("users", userService.findAll());//use Service(user) findAll- <tr th:each="user" : ${users}>

        return "/user/create";//return view html file
    }

    @PostMapping("/create")
    public String insertUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("users", userService.findAll());
            return "/user/create";
        }
        //go to create html and provide whatever create page needs (need user obj, roles, users)
        //before show all user - need to save user in map
        userService.save(user);
        return "redirect:/user/create";

    }

    //UPDATE
    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username") String username, Model model) {
        //user obj ${user}
        model.addAttribute("user", userService.findById(username));
        //role ${roles}
        model.addAttribute("roles", roleService.findAll());
        //users ${users}
        model.addAttribute("users", userService.findAll());//use Service(user) findAll- <tr th:each="user" : ${users}>
        return "/user/update";
        //update and save
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            userService.update(user);
        return "redirect:/user/create";
    }

    //DELETE
    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {//from CRUDService
        userService.deleteById(username);
        return "redirect:/user/create";
    }
}
