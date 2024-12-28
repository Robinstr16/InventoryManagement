package com.InventoryManagement.controller;

import com.InventoryManagement.Dao.BrandDao;
import com.InventoryManagement.model.Categories;
import com.InventoryManagement.security.CustomUserDetails;
import com.InventoryManagement.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("userDetails")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/Brand")
    public String viewBrand(ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and if UserDetails is available
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            modelMap.addAttribute("userDetails", userDetails);
        }
        List<Categories> allCategories=brandService.getAllCategories();
        List<BrandDao> allBrands = brandService.findAllBrandAndCategoryNames();
        modelMap.addAttribute("allCategories",allCategories);
        modelMap.addAttribute("allBrands",allBrands);
        return "brandPage";
    }

    @PostMapping("/Brand")
    public String addBrand(BrandDao brandDao, ModelMap modelMap){
        brandService.saveBrand(brandDao);
        List<BrandDao> allBrands = brandService.findAllBrandAndCategoryNames();
        modelMap.addAttribute("allBrands",allBrands);
        return "brandPage";
    }

    @GetMapping("/deleteBrand")
    public String deleteBrand(@RequestParam Long brandId,ModelMap modelMap){
        brandService.deleteBrand(brandId);
        List<BrandDao> allBrands = brandService.findAllBrandAndCategoryNames();
        modelMap.addAttribute("allBrands",allBrands);
        return "brandPage";
    }

    @PostMapping("/updateBrand")
    public String updateCategory(BrandDao brandDao,ModelMap modelMap){
        brandService.updateBrand(brandDao);
        List<BrandDao> allBrands = brandService.findAllBrandAndCategoryNames();
        modelMap.addAttribute("allBrands",allBrands);
        return "brandPage";
    }
}
