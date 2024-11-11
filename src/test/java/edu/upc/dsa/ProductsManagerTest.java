package edu.upc.dsa;

import edu.upc.dsa.exceptions.TrackNotFoundException;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.Order;
import edu.upc.dsa.models.OrderItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductsManagerTest {
    ProductsManager pm;

    @Before
    public void setUp() {
        this.pm = ProductsManagerImpl.getInstance();
        this.pm.addProduct(new Product("Aigua", 1.50));
        this.pm.addProduct(new Product("Entrepà", 4.00));
        this.pm.addProduct(new Product("Xocolata", 2.50));
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.pm.clear();
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order("user1");
        order.addProduct("Aigua", 2);
        this.pm.createOrder(order);
        List<Order> orders = this.pm.listUserOrders("user1");

        Assert.assertEquals(1, orders.size());
        Assert.assertEquals("Aigua", orders.get(0).getProductList().get(0).getProductId());
        Assert.assertEquals(2, orders.get(0).getProductList().get(0).getQuantity());

    }

    @Test
    public void testServeOrder()  {
        Order order = new Order("user2");
        order.addProduct("Entrepà", 1);

        this.pm.createOrder(order);
        this.pm.serveOrder();
        this.pm.serveOrder();


        // Verifica que la comanda s'ha servit (eliminada de la cua)

        List<Order> orders = this.pm.listUserOrders("user2");
        Assert.assertTrue(orders.contains(order)); // Comprova que encara està a la llista de l'usuari
        Assert.assertEquals(0, this.pm.getOrderQueueSize()); // Comprova que la cua està buida

    }

    @Test
    public void testListProductsByPrice() {
        List<Product> products = this.pm.listProductsByPrice();

        Assert.assertEquals(3, products.size());
        Assert.assertEquals("Aigua", products.get(0).getName());
        Assert.assertEquals("Xocolata", products.get(1).getName());
        Assert.assertEquals("Entrepà", products.get(2).getName());

    }

    @Test
    public void testListProductsBySales() {
        Order order = new Order("user1");
        order.addProduct("Aigua", 2);
        order.addProduct("Entrepà", 1);
        this.pm.createOrder(order);
        this.pm.serveOrder();

        List<Product> products = this.pm.listProductsBySales();
        Assert.assertEquals(3, products.size());
        Assert.assertEquals("Aigua", products.get(0).getName());
        Assert.assertEquals("Entrepà", products.get(1).getName());
        Assert.assertEquals("Xocolata", products.get(2).getName());

    }


}
