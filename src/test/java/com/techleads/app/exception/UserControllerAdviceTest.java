package com.techleads.app.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserControllerAdviceTest {

    @InjectMocks
    private UserControllerAdvice userControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void handleUserNotFoundException() {
        ResponseEntity<StandardError> response = userControllerAdvice.handleUserNotFoundException(
                new UserNotFoundException("User not found"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("User not found", response.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(LocalDateTime.now(), response.getBody().getLocalDateTime());


    }

    @Test
    void handleDuplicateEmailException() {
        ResponseEntity<StandardError> response = userControllerAdvice.handleDuplicateEmailException(
                new DuplicateEmailException("duplicate email"),
                new MockHttpServletRequest()
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("duplicate email", response.getBody().getError());
//        assertEquals(LocalDateTime.now(), response.getBody().getLocalDateTime());


    }
}