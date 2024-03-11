package com.myspringboot.Employee.Admin.CRUD.service;

import com.myspringboot.Employee.Admin.CRUD.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService{
    User saveUser(User user);

    User getUserById(String id);
    User updateUser(User user);
    void deleteUser(String id);

    List<User> getAllUser();

    Page<User> findPage(Integer pageNo);


}
