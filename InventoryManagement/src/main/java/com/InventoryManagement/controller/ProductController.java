package com.InventoryManagement.controller;


import com.InventoryManagement.Dao.ProductDao;
import com.InventoryManagement.model.Categories;
import com.InventoryManagement.model.Products;
import com.InventoryManagement.security.CustomUserDetails;
import com.InventoryManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("userDetails")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/Product")
    public String viewProduct(ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if user is authenticated and if UserDetails is available
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            modelMap.addAttribute("userDetails", userDetails);
        }
        List<Categories>allCategories=productService.getAllCategories();
        List<ProductDao> allProducts=productService.getAllProducts();
        modelMap.addAttribute("allCategories",allCategories);
        modelMap.addAttribute("allProducts",allProducts);
        return "productPage";
    }

    @PostMapping("/Product")
    public String addProduct(@RequestParam String productName,
                             @RequestParam Long productPrice,
                             @RequestParam Long productQty,
                             @RequestParam String brandId,
                             @RequestParam Long categoryId,
                             ModelMap modelMap){
        productService.saveProducts(productName,productPrice,productQty,categoryId,brandId);
        List<ProductDao> allProducts=productService.getAllProducts();
        modelMap.addAttribute("allProducts",allProducts);
        return "productPage";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long productId,ModelMap modelMap){
        productService.deleteProduct(productId);
        List<ProductDao> allProducts=productService.getAllProducts();
        modelMap.addAttribute("allProducts",allProducts);
        return "productPage";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestParam String upProductName,
                                @RequestParam Long upProductPrice,
                                @RequestParam Long upProductQty,
                                @RequestParam String productId,
                                ModelMap modelMap){
        productService.updateProduct(upProductName,upProductPrice,upProductQty,productId);
        List<ProductDao> allProducts=productService.getAllProducts();
        modelMap.addAttribute("allProducts",allProducts);
        return "productPage";
    }

}
