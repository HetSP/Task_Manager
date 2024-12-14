package com.example.taskmanager.service;

import com.example.taskmanager.config.JwtProvider;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSrevice {

    @Autowired
    private UserRepository repo;

    public User getUserProfile(String jwt){
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        return repo.findByEmail(email);
    }

    public List<User> getAllUsers(){
        return repo.findAll();
    }
}
