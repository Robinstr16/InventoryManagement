package com.InventoryManagement.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandDao {
    private Long brandId;
    private String brandName;
    private String categoryName;
}
