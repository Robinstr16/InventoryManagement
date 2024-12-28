package com.InventoryManagement.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String receiverName;
    private String orderBy;
    private LocalDate orderDate;
    private String receiverAddress;
    private String productDetails;
    private Long orderUnits;
    private Long amount;
    private String paymentStatus;

    private Integer deleteFlag;

    public Orders(String receiverName, String orderBy,LocalDate orderDate, String receiverAddress, String productDetails, Long orderUnits, Long amount, String paymentStatus,Integer deleteFlag) {
        this.receiverName = receiverName;
        this.orderBy=orderBy;
        this.orderDate = orderDate;
        this.receiverAddress = receiverAddress;
        this.productDetails = productDetails;
        this.orderUnits = orderUnits;
        this.amount=amount;
        this.paymentStatus = paymentStatus;
        this.deleteFlag=deleteFlag;
    }
}

