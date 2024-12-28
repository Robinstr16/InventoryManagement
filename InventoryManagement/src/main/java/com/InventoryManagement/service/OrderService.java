package com.InventoryManagement.service;


import com.InventoryManagement.Dao.ProductDao;
import com.InventoryManagement.model.Orders;
import com.InventoryManagement.model.Products;
import com.InventoryManagement.repository.OrderRepository;
import com.InventoryManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    public List<ProductDao> getAllProducts(){
        List<ProductDao> allProducts = productService.getAllProducts();
        return allProducts;
    }

    public List<Orders> getAllOrders(){
        List<Orders> allOrders = orderRepository.getAllOrders();
        return allOrders;
    }

    public void saveOrder(String receiverName, String orderBy, Date orderDate, String receiverAddress,
                          String productName,Long orderUnits,String paymentStatus) {
        LocalDate localOrderDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Products product = productRepository.findProduct(productName);
        Long totAmount = (product.getProductPrice()) * orderUnits;
        Long remProducts = (product.getProductQty()) - orderUnits;
        if (remProducts<0){
            return;
        }else if (remProducts==0){
            productRepository.deleteProduct(product.getProductId());
        }else{
            productRepository.updateQty(remProducts,product.getProductId());
        }
        Orders orders=new Orders(receiverName,orderBy,localOrderDate,receiverAddress,
                productName,orderUnits,totAmount,paymentStatus,0);
        orderRepository.save(orders);
    }

    public void deleteOrder(Long orderId){
        orderRepository.deleteOrder(orderId);
    }
}
