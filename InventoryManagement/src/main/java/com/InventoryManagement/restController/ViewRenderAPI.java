package com.InventoryManagement.restController;

import com.InventoryManagement.model.Brands;
import com.InventoryManagement.model.Categories;
import com.InventoryManagement.repository.BrandRepository;
import com.InventoryManagement.repository.CategoryRepository;
import com.InventoryManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ViewRenderAPI {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/getCategoryNames")
    public List<String> getAllCatName(){
        return categoryRepository.getAllCatName();
    }

    @PostMapping("/getBrand")
    public ResponseEntity<?> getBrands(@RequestParam Long categoryId){
        System.out.println(" categoryId "+categoryId);
        List<Brands> brands=brandRepository.findBrandByConditions(categoryId);
        return new ResponseEntity<List<Brands>>(brands, HttpStatus.OK);
    }

    @PostMapping("/getCategory")
    public ResponseEntity<?> getCategory(@RequestParam Long categoryId){
        Categories categories = categoryRepository.findById(categoryId).get();
        return new ResponseEntity<Categories>(categories,HttpStatus.OK);
    }

    @GetMapping("/getBrandNames")
    public List<String> getAllBrandName(){
        return brandRepository.getAllBrandName();
    }

    @GetMapping("/getProductNames")
    public List<String> getAllProductNames(){
        return productRepository.getAllProductNames();
    }
}
