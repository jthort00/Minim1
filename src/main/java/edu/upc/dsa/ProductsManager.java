package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Order;
import edu.upc.dsa.models.Product;

import java.util.List;

public interface ProductsManager {


    public Product addProduct(String id, String title, double price);
    public Product addProduct(String name, double price);
    public Product addProduct(Product t);

    List<Product> listProductsByPrice();
    List<Product> listProductsBySales();

    void createOrder(Order order);
    void serveOrder();
    List<Order> listUserOrders(String userId);
    int getOrderQueueSize();

    public Product getProduct(String id);
    public Product getProduct2(String id) throws TrackNotFoundException;

    public List<Product> findAll();
    public void deleteProduct(String id);
    // public Product updateProduct(Product t);

    public void clear();
    public int size();
}
