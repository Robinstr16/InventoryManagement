package com.InventoryManagement.controller;


import com.InventoryManagement.model.Categories;
import com.InventoryManagement.security.CustomUserDetails;
import com.InventoryManagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@SessionAttributes("userDetails")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/Category")
    public String viewCategory(ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and if UserDetails is available
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            System.out.println("CustomUserDetaiils"+userDetails.toString());
            modelMap.addAttribute("userDetails", userDetails);
        }
        List<Categories> allCategories=categoryService.getAllCategories();
        modelMap.addAttribute("allCategories",allCategories);
        return "categoryPage";
    }

    @PostMapping("/Category")
    public String addCategory(@RequestParam String categoryName, ModelMap modelMap){
        categoryService.saveCategory(categoryName);
        List<Categories>allCategories=categoryService.getAllCategories();
        modelMap.addAttribute("allCategories",allCategories);
        return "categoryPage";
    }

    @GetMapping("/deleteCategory")
    public String deleteCategory(@RequestParam Long categoryId,ModelMap modelMap){
        categoryService.deleteCategory(categoryId);
        List<Categories>allCategories=categoryService.getAllCategories();
        modelMap.addAttribute("allCategories",allCategories);
        return "categoryPage";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@RequestParam String categoryId,@RequestParam String upCategoryName,ModelMap modelMap){
        Long categoryIdl=Long.parseLong(categoryId);
        categoryService.updateCategory(categoryIdl,upCategoryName);
        List<Categories>allCategories=categoryService.getAllCategories();
        modelMap.addAttribute("allCategories",allCategories);
        return "categoryPage";
    }

}
