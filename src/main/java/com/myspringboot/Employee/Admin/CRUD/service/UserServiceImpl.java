package com.myspringboot.Employee.Admin.CRUD.service;

import com.myspringboot.Employee.Admin.CRUD.model.User;
import com.myspringboot.Employee.Admin.CRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        User newuser = new User(user.getEmail(), passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName(), user.getRole());
        return userRepository.save(newuser);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findPage(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 5);
        return userRepository.findAll(pageable);
    }
}
