package com.techleads.app.controller;

import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.service.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    public static final Integer ID = 1;
    public static final String USER_NOT_FOUND_EXPECTED = "User is not found with Id: " + ID;
    public static final String NAME = "madhav";
    public static final String EMAIL = "madhav@ma";
    public static final String PWD = "1234";

    private Users user;
    private UsersDTO usersDTO;

    @InjectMocks
    private UserRestController underTestUserRestController;

    @Mock
    private UsersService usersService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findUsersById() {

        when(usersService.findUserById(anyInt())).thenReturn(user);
        when(modelMapper.map(user, UsersDTO.class)).thenReturn(usersDTO);
        ResponseEntity<UsersDTO> usersById = underTestUserRestController.findUsersById(ID);
        assertNotNull(usersById);
        assertNotNull(usersById.getBody());
        assertEquals(ResponseEntity.class,usersById.getClass());
        assertEquals(ID, usersById.getBody().getId());
        assertEquals(NAME, usersById.getBody().getName());
        assertEquals(EMAIL, usersById.getBody().getEmail());
        assertEquals(PWD, usersById.getBody().getPassword());
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserById() {
    }

    private void startUser(){
        user=new Users(ID, NAME, EMAIL, PWD);
        usersDTO=new UsersDTO(ID, NAME, EMAIL, PWD);
    }
}