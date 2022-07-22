package com.techleads.app.service;

import com.techleads.app.exception.DuplicateEmailException;
import com.techleads.app.exception.UserNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.repository.UsersRepository;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    public static final Integer ID = 1;
    public static final String USER_NOT_FOUND_EXPECTED = "User is not found with Id: " + ID;
    public static final String NAME = "madhav";
    public static final String EMAIL = "madhav@ma";
    public static final String PWD = "1234";
    @InjectMocks
    private UsersService underTestUsersService;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private ModelMapper modelMapper;
    private Users user;
    private UsersDTO usersDTO;
    private Optional<Users> optionalUsers;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findUserByIdTest() {
        when(usersRepository.findById(anyInt())).thenReturn(optionalUsers);
        Users userResponse = underTestUsersService.findUserById(ID);
        assertNotNull(userResponse);
        assertEquals(Users.class, userResponse.getClass());
        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
        verify(usersRepository, times(1)).findById(anyInt());
    }

    @Test
    void findUserByIdTestThrowUserNotFoundException() {

        when(usersRepository.findById(anyInt())).thenThrow(new UserNotFoundException(USER_NOT_FOUND_EXPECTED));
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> {

            Users userResponse = underTestUsersService.findUserById(ID);
        });
        assertEquals(USER_NOT_FOUND_EXPECTED, userNotFoundException.getMessage());

        verify(usersRepository, times(1)).findById(anyInt());
    }

    @Test
    void TestFindAllUsers() {
        when(usersRepository.findAll()).thenReturn(List.of(user));
        List<Users> reponse = underTestUsersService.findAllUsers();
        assertNotNull(reponse);
        assertEquals(1,reponse.size());
        assertEquals(Users.class, reponse.get(0).getClass());
        assertEquals(ID, reponse.get(0).getId());
        assertEquals(NAME, reponse.get(0).getName());
        assertEquals(EMAIL, reponse.get(0).getEmail());
        assertEquals(PWD, reponse.get(0).getPassword());
    }

    @Test
    void TestSaveUser() {
        when(usersRepository.save(any())).thenReturn(user);
        Users users = underTestUsersService.saveUser(usersDTO);
        assertEquals(Users.class, users.getClass());
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PWD, user.getPassword());
        verify(usersRepository, times(1)).save(any());

    }

    @Test
    void TestSaveUserThrowDuplicateEmailException() {
        when(usersRepository.findUserByEmail(any())).
                thenReturn(optionalUsers);
        DuplicateEmailException duplicateEmailException = assertThrows(DuplicateEmailException.class, () -> {
            optionalUsers.get().setId(2);
            underTestUsersService.saveUser(usersDTO);
        });
        assertEquals("Email id: " + user.getEmail() + " is already exists",duplicateEmailException.getMessage());

        verify(usersRepository, times(1)).findUserByEmail(any());

    }

    @Test
    void testUpdateUser() {

        when(usersRepository.save(any())).thenReturn(user);
        Users users = underTestUsersService.updateUser(usersDTO);
        assertEquals(Users.class, users.getClass());
        assertEquals(ID, user.getId());
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PWD, user.getPassword());
        verify(usersRepository, times(1)).save(any());

    }

    @Test
    void TestUpdateUserThrowDuplicateEmailException() {
        when(usersRepository.findUserByEmail(any())).
                thenReturn(optionalUsers);
        DuplicateEmailException duplicateEmailException = assertThrows(DuplicateEmailException.class, () -> {
            optionalUsers.get().setId(2);
            underTestUsersService.saveUser(usersDTO);
        });
        assertEquals("Email id: " + user.getEmail() + " is already exists",duplicateEmailException.getMessage());

        verify(usersRepository, times(1)).findUserByEmail(any());

    }

    @Test
    void testDeleteUserById() {
        when(usersRepository.findById(any())).thenReturn(optionalUsers);
        doNothing().when(usersRepository).deleteById(anyInt());
        underTestUsersService.deleteUserById(ID);
        verify(usersRepository, times(1)).findById(anyInt());
    }

    @Test
    void testDeleteUserByIdThrowUserNotFoundException() {
        when(usersRepository.findById(anyInt())).thenThrow(new UserNotFoundException(USER_NOT_FOUND_EXPECTED));
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> {

            underTestUsersService.deleteUserById(ID);
        });
        assertEquals(USER_NOT_FOUND_EXPECTED, userNotFoundException.getMessage());

        verify(usersRepository, times(1)).findById(anyInt());
        verify(usersRepository, times(0)).deleteById(anyInt());

    }

    private void startUser(){
        user=new Users(ID, NAME, EMAIL, PWD);
        usersDTO=new UsersDTO(ID, NAME, EMAIL, PWD);
        optionalUsers=Optional.of(new Users(ID, NAME, EMAIL, PWD));
    }
}