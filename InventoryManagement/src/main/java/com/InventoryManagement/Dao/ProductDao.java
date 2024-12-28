package com.InventoryManagement.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDao {
    private Long productId;
    private String categoryName;
    private String brandName;
    private String productName;
    private Long productPrice;
    private Long productQty;
}
