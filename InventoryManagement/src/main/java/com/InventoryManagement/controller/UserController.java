package com.InventoryManagement.controller;


import com.InventoryManagement.Dao.UserDao;
import com.InventoryManagement.model.Categories;
import com.InventoryManagement.model.User;
import com.InventoryManagement.security.CustomUserDetails;
import com.InventoryManagement.service.CategoryService;
import com.InventoryManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;


@Controller
@SessionAttributes("userDetails")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(UserDao userDao){
        userService.save(userDao);
        return "login";
    }

    @GetMapping("/home")
    public String home(ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and if UserDetails is available
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            modelMap.addAttribute("userDetails", userDetails);
        }
        List<Categories> allCategories=categoryService.getAllCategories();
        modelMap.addAttribute("allCategories",allCategories);
        return "home";
    }

    @GetMapping("/userList")
    public String userList(ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and if UserDetails is available
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            modelMap.addAttribute("userDetails", userDetails);
        }
        List<User> allUsers=userService.getAllUsers();
        modelMap.addAttribute("allUsers",allUsers);
        return "userPage";
    }

}
