package com.myspringboot.Employee.Admin.CRUD.controller;

import com.myspringboot.Employee.Admin.CRUD.model.User;
import com.myspringboot.Employee.Admin.CRUD.service.EmployeeUserDetails;
import com.myspringboot.Employee.Admin.CRUD.service.EmployeeUserDetailsService;
import com.myspringboot.Employee.Admin.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    public EmployeeUserDetailsService employeeUserDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/registration")
    public String getRegisterPage(@ModelAttribute("user") User user){
        return "register";
    }

    @PostMapping("/registration")
    public String saveRegisterUser(@ModelAttribute("user") User user, Model model){
        if(user.getFirstName().equals("Sanskar")){
            user.setRole("ADMIN");
        }else{
            user.setRole("USER");
        }
        userService.saveUser(user);
        model.addAttribute("message", "Registered Successfully!!");
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal){
        UserDetails empDetails = employeeUserDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", empDetails);
        return "user";
    }

    @GetMapping("/admin-page")
    public String getAdminPage(Model model, Principal principal){
        UserDetails empDetails = employeeUserDetailsService.loadUserByUsername(principal.getName());
        User adminUser = employeeUserDetailsService.loadUserfromService(principal.getName());
        /*List<User>  allusers = userService.getAllUser();
        model.addAttribute("user", empDetails);
        model.addAttribute("alluser", allusers);
        model.addAttribute("adminuser", adminUser);*/
        return getOnePage(1, model, principal);
    }

    @GetMapping("/update-user/{username}")
    public String getUpdateForm(@PathVariable String username, Model model){
        //UserDetails empDetails = employeeUserDetailsService.loadUserByUsername(username);
        User user = employeeUserDetailsService.loadUserfromService(username);
        model.addAttribute("user", user);
        return "update-user";
    }

    @GetMapping("/update-user/{adminemail}/{username}")
    public String updateFromAdmin(@PathVariable String adminemail, @PathVariable String username, Model model){
        User user = employeeUserDetailsService.loadUserfromService(username);
        model.addAttribute("user", user);
        model.addAttribute("adminemail", adminemail);
        return "update-admin";
    }

    @PostMapping("/admin-page")
    public String redirectToAdmin(@ModelAttribute("user") User user, Model model, Principal principal){
        User newuser = employeeUserDetailsService.loadUserfromService(user.getEmail());
        newuser.setFirstName(user.getFirstName());
        newuser.setLastName(user.getLastName());
        newuser.setEmail(user.getEmail());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(newuser);
        UserDetails empuser = employeeUserDetailsService.loadUserByUsername(principal.getName());
        User adminUser = employeeUserDetailsService.loadUserfromService(principal.getName());
        model.addAttribute("user", empuser);
        model.addAttribute("message", "User details updated successfully!!");
        model.addAttribute("adminuser", adminUser);
        List<User>  allusers = userService.getAllUser();
        model.addAttribute("alluser", allusers);
        return "admin";
    }

    @PostMapping("/user-page")
    public String updateUser(@ModelAttribute("user") User user, Model model){
        User newuser = employeeUserDetailsService.loadUserfromService(user.getEmail());
        newuser.setFirstName(user.getFirstName());
        newuser.setLastName(user.getLastName());
        newuser.setEmail(user.getEmail());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(newuser);
        UserDetails empuser = employeeUserDetailsService.loadUserByUsername(newuser.getEmail());
        model.addAttribute("user", empuser);
        model.addAttribute("message", "User details updated successfully!!");
        return "user";
    }


    @GetMapping("/delete-user/{adminemail}/{id}")
    public String deleteFromAdmin(@PathVariable String adminemail, @PathVariable String id, Principal principal, Model model){
        userService.deleteUser(id);
        List<User> allusers = userService.getAllUser();
        UserDetails empuser = employeeUserDetailsService.loadUserByUsername(principal.getName());
        User adminUser = employeeUserDetailsService.loadUserfromService(principal.getName());
        model.addAttribute("alluser", allusers);
        model.addAttribute("user", empuser);
        model.addAttribute("adminuser", adminUser);
        return "admin";
    }
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable String id, Model model){
        userService.deleteUser(id);
        model.addAttribute("message", "User deleted successfully!!");
        model.addAttribute("user", new User());
        return "register";
    }

    //pagination
    @GetMapping("/employee/page/{pageNumber}")
    public String getOnePage(@PathVariable("pageNumber") Integer currentPage, Model model, Principal principal){
        Page<User> page = userService.findPage(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<User> allusers = page.getContent();
        UserDetails empuser = employeeUserDetailsService.loadUserByUsername(principal.getName());
        User adminUser = employeeUserDetailsService.loadUserfromService(principal.getName());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("alluser", allusers);
        model.addAttribute("user", empuser);
        model.addAttribute("adminuser", adminUser);
        return "admin";
    }


}
