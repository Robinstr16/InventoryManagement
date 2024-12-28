package com.InventoryManagement.service;

import com.InventoryManagement.model.User;
import com.InventoryManagement.Dao.UserDao;
import com.InventoryManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User save(UserDao userDao){
        User user =new User(userDao.getEmail(),passwordEncoder.encode(userDao.getPassword()),userDao.getRole(),userDao.getName());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }
}

