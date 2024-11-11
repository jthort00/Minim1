package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Product {

    String id;
    String name;
    double price;
    int sales = 0;

    public Product() {
        this.setId(RandomUtils.getId());
    }
    public Product(String name, double price) {
        this(null, name, price);
    }

    public Product(String id, String name, double price) {
        this();
        if (id != null) this.setId(id);
        this.setPrice(price);
        this.setName(name);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void incrementSales(int quantity) {
        this.sales += quantity;
    }


    @Override
    public String toString() {
        return "Product [id="+id+", name=" + name + ", price=" + price +"]";
    }

}