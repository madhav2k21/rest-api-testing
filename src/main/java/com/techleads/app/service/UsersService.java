package com.techleads.app.service;

import com.techleads.app.exception.DuplicateEmailException;
import com.techleads.app.exception.UserNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Users findUserById(Integer id) {
        Optional<Users> userById = usersRepository.findById(id);
        return userById.orElseThrow(
                () -> new UserNotFoundException("User is not found with Id: " + id)
        );
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public Users saveUser(UsersDTO user) {
        checkUniqueEmailAddress(user);

        return usersRepository.save(modelMapper.map(user, Users.class));
    }

    private void checkUniqueEmailAddress(UsersDTO user) {
        Optional<Users> userByEmail = usersRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent() && (user.getId() != userByEmail.get().getId())) {
            throw new DuplicateEmailException("Email id: " + user.getEmail() + " is already exists");
        }
    }

    public Users updateUser(UsersDTO user) {
        checkUniqueEmailAddress(user);
        return usersRepository.save(modelMapper.map(user, Users.class));
    }

    public void deleteUserById(Integer id) {
        findUserById(id);
        usersRepository.deleteById(id);
    }
}
