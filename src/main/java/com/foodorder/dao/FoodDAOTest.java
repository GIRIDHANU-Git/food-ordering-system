
package com.foodorder.dao;

import com.foodorder.model.Food;
import java.sql.SQLException;
import java.util.List;

public class FoodDAOTest {

    public static void main(String[] args) {
        FoodDAO foodDAO = new FoodDAO();

        try {
            List<Food> foods = foodDAO.getAllAvailableFoods();

            for (Food food : foods) {
                System.out.printf(
                        "%s | %s | ₹%s%n",
                        food.getName(),
                        food.getCategory(),
                        food.getPrice()
                );
            }

            System.out.println("Total foods: " + foods.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
