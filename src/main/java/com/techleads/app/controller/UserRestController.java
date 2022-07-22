package com.techleads.app.controller;

import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<UsersDTO>> findAllUsers() {
        List<Users> allUsers = usersService.findAllUsers();

        List<UsersDTO> userDTOs = convertToUserDTOs(allUsers);

        return ResponseEntity.ok().body(userDTOs);
    }

    private List<UsersDTO> convertToUserDTOs(List<Users> allUsers) {
        List<UsersDTO> userDTOs = allUsers.stream().
                map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
        return userDTOs;
    }

    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO user) {
        Users createdUser = usersService.saveUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<UsersDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UsersDTO user) {
        user.setId(id);
        Users updatedUser = usersService.updateUser(user);
        return ResponseEntity.ok().body(modelMapper.map(updatedUser, UsersDTO.class));
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<UsersDTO> deleteUserById(@PathVariable("id") Integer id){
        usersService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}


