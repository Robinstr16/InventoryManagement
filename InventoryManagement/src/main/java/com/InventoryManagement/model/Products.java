package com.InventoryManagement.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long productQty;
    private Long categoryId;
    private Long brandId;
    private Integer deleteFlag;

    public Products(String productName,Long productPrice,Long productQty,Long categoryId,Long brandId,Integer deleteFlag){
        this.productName=productName;
        this.productPrice=productPrice;
        this.productQty=productQty;
        this.categoryId=categoryId;
        this.brandId=brandId;
        this.deleteFlag=deleteFlag;
    }

}
