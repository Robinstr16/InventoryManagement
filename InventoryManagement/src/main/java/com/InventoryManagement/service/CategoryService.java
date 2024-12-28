package com.InventoryManagement.service;


import com.InventoryManagement.model.Categories;
import com.InventoryManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandService brandService;

    public List<Categories> getAllCategories(){
        List<Categories> allCategories=categoryRepository.getAllCategories();
        List<String> allCatNames=categoryRepository.getAllCatName();
        return allCategories;
    }


    public void saveCategory(String categoryName){
        if (categoryRepository.existsByCategoryName(categoryName)) {
            categoryRepository.retainCategory(categoryName);
        } else {
            Categories categories = new Categories(categoryName, 0);
            categoryRepository.save(categories);
        }
    }

    public void deleteCategory(Long categoryId){
        HashSet<Long> allBrands = brandService.getAllCatgryIdInBrand();
        if (allBrands.contains(categoryId)){
            return;
        }
        categoryRepository.deleteCategory(categoryId);
    }

    public void updateCategory(Long categoryId,String categoryName){
        categoryRepository.updateCategory(categoryId,categoryName);
    }
}
