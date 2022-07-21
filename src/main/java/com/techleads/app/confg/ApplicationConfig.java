package com.techleads.app.confg;

import com.techleads.app.model.Users;
import com.techleads.app.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("local")
public class ApplicationConfig {

    @Autowired
    private UsersRepository usersRepository;
    @Bean
    public void setupDB(){
        Users u1=new Users(null, "madhav", "madhav@us", "1234");
        Users u2=new Users(null, "dill", "dill@us", "1234");
        usersRepository.saveAll(Arrays.asList(u1,u2));
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
