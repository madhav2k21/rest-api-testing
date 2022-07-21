package com.techleads.app.controller;

import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/users"})
public class UserRestController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<UsersDTO> findUsersById(@PathVariable("id") Integer id) {
        Users user = usersService.findUserById(id);
        UsersDTO userDTO = modelMapper.map(user, UsersDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<Users>> findAllUsers() {
        List<Users> allUsers = usersService.findAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        return null;
    }
}


