
package com.foodorder.model;

import java.math.BigDecimal;

public class OrderItem {

    private long foodItemId;
    private String foodName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public OrderItem(long foodItemId, String foodName,
                     int quantity, BigDecimal unitPrice,
                     BigDecimal subtotal) {
        this.foodItemId = foodItemId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public long getFoodItemId() {
        return foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
