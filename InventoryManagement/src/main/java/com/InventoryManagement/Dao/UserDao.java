package com.InventoryManagement.Dao;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDao {
    private String email;
    private String password;
    private String role;
    private String name;
}
