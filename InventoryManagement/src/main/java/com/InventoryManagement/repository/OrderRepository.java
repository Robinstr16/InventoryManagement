package com.InventoryManagement.repository;


import com.InventoryManagement.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    @Query("select o from Orders o where o.deleteFlag=0")
    List<Orders> getAllOrders();

    @Modifying
    @Transactional
    @Query("update Orders o set o.deleteFlag = 1 where o.orderId = :orderId")
    public void deleteOrder(Long orderId);
}

