package com.techleads.app.service;

import com.techleads.app.exception.UserNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Users findUserById(Integer id) {
        Optional<Users> userById = usersRepository.findById(id);
        return userById.orElseThrow(
                () -> new UserNotFoundException("User is found with Id: " + id)
        );
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public String saveUser(Users user) {
        Users createdUser = usersRepository.save(user);
        if (Objects.nonNull(createdUser)) {
            return "User is created";
        }
        return "User not created";

    }
}
