
package com.foodorder.model;

import java.math.BigDecimal;

public class Food {
    private long id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private boolean available;

    public Food(long id, String name, String description,
                String category, BigDecimal price, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}
