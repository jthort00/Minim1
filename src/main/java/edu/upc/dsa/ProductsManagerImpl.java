package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Order;
import edu.upc.dsa.models.Product;

import java.util.*;

import org.apache.log4j.Logger;

public class ProductsManagerImpl implements ProductsManager {
    private static ProductsManager instance;
    // protected List<Product> ProductManagerImpl;
    final static Logger logger = Logger.getLogger(ProductsManagerImpl.class);
    private List<Product> productList;
    private Queue<Order> orderQueue;
    private Map<String, List<Order>> userOrders;



    private ProductsManagerImpl() {
        // this.ProductManagerImpl = new LinkedList<>();
        this.productList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
        this.userOrders = new HashMap<>();

    }

    public static ProductsManager getInstance() {
        if (instance==null) instance = new ProductsManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.productList.size();
        logger.info("size " + ret);

        return ret;
    }

    public Product addProduct(Product t) {
        logger.info("new Product " + t);

        this.productList.add (t);
        logger.info("new Product added");
        return t;
    }

    public Product addProduct(String name, double price){
        return this.addProduct(null, name, price);
    }

    public Product addProduct(String id, String name, double price) {
        return this.addProduct(new Product(id, name, price));
    }

    public Product getProduct(String id) {
        logger.info("getProduct("+id+")");

        for (Product t: this.productList) {
            if (t.getId().equals(id)) {
                logger.info("getProduct("+id+"): "+t);

                return t;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    public Product getProduct2(String id) throws TrackNotFoundException {
        Product t = getProduct(id);
        if (t == null) throw new TrackNotFoundException();
        return t;
    }


    public List<Product> findAll() {
        return this.productList;
    }

    @Override
    public List<Product> listProductsByPrice() {
        logger.info("Listing products by price");
        productList.sort(Comparator.comparingDouble(Product::getPrice));
        return new ArrayList<>(productList);
    }

    @Override
    public List<Product> listProductsBySales() {
        logger.info("Listing products by sales");
        productList.sort(Comparator.comparingInt(Product::getSales).reversed());
        return new ArrayList<>(productList);
    }

    @Override
    public void deleteProduct(String id) {

        Product t = this.getProduct(id);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.productList.remove(t);

    }


    @Override
    public void createOrder(Order order) {
        logger.info("Adding an order for user:" + order.getUserId());
        orderQueue.add(order);
        userOrders.computeIfAbsent(order.getUserId(), k -> new ArrayList<>()).add(order);

    }

    @Override
    public List<Order> listUserOrders(String userId) {
        logger.info("Listing orders for user: " + userId);
        return userOrders.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public void serveOrder() {
        if (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            logger.info("Order served: " + order);
            // Process the order here (e.g., mark as served)
        } else {
            logger.error("No orders to serve");
        }
    }

    @Override
    public int getOrderQueueSize() {
        return orderQueue.size();
    }



    public void clear() {
        this.productList.clear();
    }
}