package com.myspringboot.Employee.Admin.CRUD.service;

import com.myspringboot.Employee.Admin.CRUD.model.User;
import com.myspringboot.Employee.Admin.CRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);  //username is email see login.html
        if(user == null){
            throw new UsernameNotFoundException("User not found!!");
        }
        return new EmployeeUserDetails(user);
    }

    public User loadUserfromService(String username){
        User user = userRepository.findByEmail(username);
        return user;
    }
}
