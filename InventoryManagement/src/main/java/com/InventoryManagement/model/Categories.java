package com.InventoryManagement.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private Integer deleteFlag;

    public Categories(String categoryName,Integer deleteFlag){
        this.categoryName=categoryName;
        this.deleteFlag=deleteFlag;
    }
}
