package com.InventoryManagement.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String brandName;
    private Long categoryId;
    private Integer deleteFlag;

    public Brands(String brandName,Long categoryId,Integer deleteFlag){
        this.brandName=brandName;
        this.categoryId=categoryId;
        this.deleteFlag=deleteFlag;
    }

}
