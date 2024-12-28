package com.InventoryManagement.service;

import com.InventoryManagement.Dao.BrandDao;
import com.InventoryManagement.model.Brands;
import com.InventoryManagement.model.Categories;
import com.InventoryManagement.model.Products;
import com.InventoryManagement.repository.BrandRepository;
import com.InventoryManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BrandService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandRepository brandRepository;

    public void saveBrand(BrandDao brandDao){
        Categories categories=categoryRepository.findByCategoryName(brandDao.getCategoryName());
        Long id =categories.getCategoryId();
        Brands brands=new Brands(brandDao.getBrandName(),id,0);
        brandRepository.save(brands);
    }
//
//    public List<Brands> getAllBrands(){
//        List<Brands> allBrands=brandRepository.getAllBrands();
//        List<Object[]> allBrandsTest = brandRepository.findAllBrandAndCategoryNames();
//        System.out.println("allBrands :"+ allBrandsTest);
//        for (Object[] object: allBrandsTest) {
//            System.out.println("brand :"+ Arrays.toString(object));
//        }
//        return allBrands;
//    }

    public List<BrandDao> findAllBrandAndCategoryNames(){
        List<Object[]> allBrands = brandRepository.findAllBrandAndCategoryNames();
        List<BrandDao> brandDaoList = new ArrayList<>();
        for (Object[] object: allBrands) {
            System.out.println("brand :"+ Arrays.toString(object));
            Long id = Long.valueOf(object[0].toString());
            BrandDao brandDao =new BrandDao(id,object[1].toString(),object[2].toString());
            brandDaoList.add(brandDao);
        }
        return brandDaoList;
    }

    public HashSet<Long> getAllCatgryIdInBrand(){
        List<Brands> allBrandList=brandRepository.getAllBrands();
        HashSet<Long> allBrands = new HashSet<>();
        for (Brands brand:allBrandList){
            allBrands.add(brand.getCategoryId());
        }
        return allBrands;
    }

    public List<Categories> getAllCategories(){
        List<Categories> allCategories=categoryRepository.getAllCategories();
        return allCategories;
    }

    public void deleteBrand(Long brandId){
        Hashtable<Long, Products> allProducts= productService.getAllProductWithKey();
        if(allProducts.containsKey(brandId)){
            return;
        }
        brandRepository.deleteBrand(brandId);
    }

    public void updateBrand(BrandDao brandDao){
        brandRepository.updateBrand(brandDao.getBrandId(),brandDao.getBrandName());
    }

}
