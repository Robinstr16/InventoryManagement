package com.InventoryManagement.repository;

import com.InventoryManagement.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories,Long> {

    Categories findByCategoryName(String categoryName);

    @Query("select c from Categories c where c.deleteFlag=0")
    List<Categories>  getAllCategories();

    @Query("Select c.categoryName from Categories c where c.deleteFlag=0")
    List<String> getAllCatName();

    @Modifying
    @Transactional
    @Query("update Categories c set c.deleteFlag = 1 where c.categoryId = :categoryId")
    public void deleteCategory(Long categoryId);


    public boolean existsByCategoryName(String categoryName);

    @Modifying
    @Transactional
    @Query("update Categories c set c.deleteFlag=0 where c.categoryName = :categoryName")
    public void retainCategory(String categoryName);


    @Modifying
    @Transactional
    @Query("update Categories c set c.categoryName = :categoryName where c.categoryId = :categoryId")
    public void updateCategory(Long categoryId,String categoryName);



}
