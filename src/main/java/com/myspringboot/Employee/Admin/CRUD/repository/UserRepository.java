package com.myspringboot.Employee.Admin.CRUD.repository;

import com.myspringboot.Employee.Admin.CRUD.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);


}
