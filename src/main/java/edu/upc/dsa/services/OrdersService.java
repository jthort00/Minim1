package edu.upc.dsa.services;


import edu.upc.dsa.ProductsManager;
import edu.upc.dsa.ProductsManagerImpl;
import edu.upc.dsa.models.Product;
import edu.upc.dsa.models.Order;
import edu.upc.dsa.models.OrderItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/orders", description = "Endpoint to Orders Service")
@Path("/orders")
public class OrdersService {

    private ProductsManager pm;

    public OrdersService() {
        this.pm = ProductsManagerImpl.getInstance();

        this.pm.addProduct(new Product("Aigua", 1.50));
        this.pm.addProduct(new Product("Entrepà", 4.00));
        this.pm.addProduct(new Product("Xocolata", 2.50));
        this.pm.addProduct(new Product("Patates", 2.00));
        this.pm.addProduct(new Product("Coca-Cola", 1.75));
        this.pm.addProduct(new Product("Olives", 3.00));
    }

    @GET
    @ApiOperation(value = "get all products", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })

    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnAll() {

        List<Product> products = this.pm.findAll();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get all products sorted by price", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })

    @Path("/sortbyprice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {

        List<Product> products = this.pm.listProductsByPrice();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "get all products sorted by sales", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })

    @Path("/sortbysales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {

        List<Product> products = this.pm.listProductsBySales();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "get the orders of a customer", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Order.class, responseContainer="List"),
    })

    @Path("/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrders(@PathParam("user_id") String user_id) {

        List<Order> orders = this.pm.listUserOrders(user_id);

        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(orders) {};
        return Response.status(201).entity(entity).build()  ;
    }

    @GET
    @ApiOperation(value = "serve an order", notes = "aaa")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successful")})

    @Path("/serveorder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response serveOrder() {

        this.pm.serveOrder();
        return Response.status(201).build()  ;
    }

    @POST
    @ApiOperation(value = "make a new order", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Order.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addorder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newOrder(Order order) {

        if (order.getProductList()==null || order.getUserId()==null)  return Response.status(500).entity(order).build();
        this.pm.createOrder(order);
        return Response.status(201).entity(order).build();
    }

}