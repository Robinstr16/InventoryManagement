package com.InventoryManagement.repository;


import com.InventoryManagement.model.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brands,Long> {

    public Brands findByBrandName(String brandName);

    public List<Brands> findByCategoryId(Long categoryId);

    @Query("SELECT CONCAT(c.categoryName, ' ', b.brandName) FROM Brands b JOIN Categories c ON b.categoryId = c.categoryId WHERE b.deleteFlag = 0")
    public List<String> getAllBrandName();


    @Query("select b from Brands b where b.deleteFlag=0")
    List<Brands>  getAllBrands();

    /*
    @Query("select b.brandId,c.categoryName,b.brandName from Brands b join Categories c on b.categoryId = c.categoryId")
    List<Brands> getAllBrandsJoin();
     */

    @Query("SELECT b.brandId, b.brandName, c.categoryName FROM Brands b " +
            "JOIN Categories c ON b.categoryId = c.categoryId where b.deleteFlag=0")
    List<Object[]> findAllBrandAndCategoryNames();


    @Modifying
    @Transactional
    @Query("select b FROM Brands b where b.deleteFlag = 0 AND b.categoryId = :categoryId")
    List<Brands> findBrandByConditions(Long categoryId);

    @Modifying
    @Transactional
    @Query("update Brands b set b.deleteFlag = 1 where b.brandId = :brandId")
    public void deleteBrand(Long brandId);

    @Modifying
    @Transactional
    @Query("update Brands b set b.brandName = :brandName where brandId = :brandId ")
    public void updateBrand(Long brandId,String brandName);
}

