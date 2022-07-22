package com.techleads.app.controller;

import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.service.UsersService;
import org.h2.engine.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    private ServletUriComponentsBuilder servletUriComponentsBuilder;

    @Mock
    private UsersService usersService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findUsersById() {

        when(usersService.findUserById(anyInt())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(usersDTO);
        ResponseEntity<UsersDTO> usersById = underTestUserRestController.findUsersById(ID);
        assertNotNull(usersById);
        assertNotNull(usersById.getBody());
        assertEquals(ResponseEntity.class, usersById.getClass());
        assertEquals(ID, usersById.getBody().getId());
        assertEquals(NAME, usersById.getBody().getName());
        assertEquals(EMAIL, usersById.getBody().getEmail());
        assertEquals(PWD, usersById.getBody().getPassword());
    }

    @Test
    void findAllUsers() {
        //GIVEN
        Users user2 = new Users(101, "dill", "dill@", "dill123");
        UsersDTO dto = new UsersDTO(101, "dill", "dill@", "dill123");
        List<Users> users = Arrays.asList(user, user2);
        List<UsersDTO> usersDTOS = Arrays.asList(usersDTO, dto);

        when(usersService.findAllUsers()).thenReturn(users);
        when(modelMapper.map(any(), any())).thenReturn(usersDTO);

        ResponseEntity<List<UsersDTO>> allUsers = underTestUserRestController.findAllUsers();
        assertNotNull(allUsers);
        assertEquals(2, allUsers.getBody().size());
        assertEquals(ID, allUsers.getBody().get(0).getId());
        assertEquals(HttpStatus.OK, allUsers.getStatusCode());
        assertEquals(ArrayList.class, allUsers.getBody().getClass());
//        assertEquals(101, allUsers.getBody().get(1).getId());
    }

    @Test
    void testCreateUser() {
        when(usersService.saveUser(any())).thenReturn(user);

        ResponseEntity<UsersDTO> userReponse = underTestUserRestController.createUser(usersDTO);
        assertNotNull(userReponse);
        assertEquals(HttpStatus.CREATED, userReponse.getStatusCode());
        assertNotNull(userReponse.getHeaders().get("Location"));
    }

    @Test
    void updateUser() {
        when(usersService.updateUser(usersDTO)).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(usersDTO);
        ResponseEntity<UsersDTO> userReponse = underTestUserRestController.updateUser(ID, usersDTO);

        assertNotNull(userReponse);
        assertEquals(HttpStatus.OK, userReponse.getStatusCode());
    }

    @Test
    void deleteUserById() {
    doNothing().when(usersService).deleteUserById(anyInt());
        ResponseEntity<UsersDTO> response = underTestUserRestController.deleteUserById(ID);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usersService, times(1)).deleteUserById(anyInt());
    }

    private void startUser() {
        user = new Users(ID, NAME, EMAIL, PWD);
        usersDTO = new UsersDTO(ID, NAME, EMAIL, PWD);
    }
}