package com.InventoryManagement.repository;

import com.InventoryManagement.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {


    @Query("select p from Products p where p.productName =:productName AND p.deleteFlag=0")
    public Products findProduct(@Param("productName")String productName);

    @Query("select p from Products p where p.deleteFlag=0")
    List<Products> getAllProducts();

/*
    @Query("SELECT p.productId, c.categoryName, b.brandName, p.productName, p.productPrice, p.productQty FROM Products p " +
            "JOIN p.category c " +
            "JOIN p.brand b WHERE p.deleteFlag = 0")
    List<Object[]> getAllProduct();


 */

    @Query("SELECT p.brandId, c.categoryName, b.brandName, p.productName, p.productPrice, p.productQty " +
            "FROM Products p " +
            "JOIN Categories c ON p.categoryId = c.categoryId "+
            "JOIN Brands b ON p.brandId = b.brandId ")
            List<Object[]> getAllProduct();

    @Modifying
    @Transactional
    @Query("update Products p set p.productQty = :remainQty where p.productId = :productId")
    public void updateQty(Long remainQty,Long productId);


    @Modifying
    @Transactional
    @Query("update Products p set p.deleteFlag = 1 where p.productId = :productId")
    public void deleteProduct(Long productId);

    @Modifying
    @Transactional
    @Query("update Products p set p.productName = :productName,p.productPrice =:productPrice,p.productQty = :productQty where p.productId = :productId ")
    public void updateProduct(String productName,Long productPrice,Long productQty,Long productId);

    @Query("select p.productName from Products p where p.deleteFlag=0")
    public List<String> getAllProductNames();
}

