package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String userId;
    private List<OrderItem> productList;

    public Order(String userId) {
        this.userId = userId;
        this.productList = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderItem> getProductList() {
        return productList;
    }

    public void addProduct(String productId, int quantity) {
        this.productList.add(new OrderItem(productId, quantity));
    }

    @Override
    public String toString() {
        return "Order [userId=" + userId + ", productList=" + productList + "]";
    }
}
