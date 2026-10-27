
package com.foodorder.model;

import java.math.BigDecimal;

public class CartItem {
    private final Food food;
    private int quantity;

    public CartItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException(
                    "Quantity must be at least 1"
            );
        }
        this.quantity = quantity;
    }


    public BigDecimal getSubtotal() {
        return food.getPrice()
                .multiply(BigDecimal.valueOf(quantity));
    }

}
