package com.myspringboot.Employee.Admin.CRUD.service;

import com.myspringboot.Employee.Admin.CRUD.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class EmployeeUserDetails implements UserDetails {

    @Autowired
    private User user;

    public EmployeeUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> user.getRole());
    }

    public String getFullName(){
        return user.getFirstName() + " " + user.getLastName();
    }

    public String getEmail() {
        return user.getEmail();
    }
    public String getId(){
        return user.getId();
    }
    public String getFirstName() { return  user.getFirstName();}
    public String getLastName(){
        return user.getLastName();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
