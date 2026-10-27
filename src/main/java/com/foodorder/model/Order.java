
package com.foodorder.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {

    private long id;
    private BigDecimal totalAmount;
    private String status;
    private Timestamp createdAt;
    private String customerName;
    private String phone;
    private String deliveryAddress;

    public Order(long id, BigDecimal totalAmount,
                 String status, Timestamp createdAt,
                 String customerName, String phone,
                 String deliveryAddress) {

        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.customerName = customerName;
        this.phone = phone;
        this.deliveryAddress = deliveryAddress;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
