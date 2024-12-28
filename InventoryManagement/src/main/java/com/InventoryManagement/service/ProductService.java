package com.InventoryManagement.service;

import com.InventoryManagement.Dao.ProductDao;
import com.InventoryManagement.model.Categories;
import com.InventoryManagement.model.Products;
import com.InventoryManagement.repository.BrandRepository;
import com.InventoryManagement.repository.CategoryRepository;
import com.InventoryManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Categories> getAllCategories(){
        List<Categories> allCategories=categoryRepository.getAllCategories();
        return allCategories;
    }

    public Hashtable<Long, Products> getAllProductWithKey(){
        List<Products> allProductList=productRepository.getAllProducts();
        Hashtable<Long,Products> allProducts = new Hashtable<>();
        for (Products product:allProductList){
            allProducts.put(product.getProductId(),product);
        }
        return allProducts;
    }


    public List<ProductDao> getAllProducts(){
        List<Object[]> allProducts=productRepository.getAllProduct();
        List<ProductDao> productDaoList = new ArrayList<>();
        for (Object[] object: allProducts) {
            System.out.println("product :"+ Arrays.toString(object));
            Long id = Long.valueOf(object[0].toString());
            ProductDao productDao =new ProductDao(id,object[1].toString(),object[2].toString(),object[3].toString(),Long.valueOf(object[4].toString()),Long.valueOf(object[5].toString()));
            productDaoList.add(productDao);
        }
        return productDaoList;
    }





    public void saveProducts(String productName, Long productPrice, Long productQty, Long categoryId,String brandId){

        Long brandIdL = Long.valueOf(brandId);
        Products products=new Products(productName,productPrice,productQty,categoryId,brandIdL,0);
        productRepository.save(products);
    }

    public void deleteProduct(Long productId){
        productRepository.deleteProduct(productId);
    }

    public void updateProduct(String productName,Long productPrice,Long productQty,String productId){
        Long productIdl = Long.valueOf(productId);
        productRepository.updateProduct(productName,productPrice,productQty,productIdl);
    }
}

