package com.bookstore_csa_cw.resource;

import com.bookstore_csa_cw.dao.BookDAO;
import com.bookstore_csa_cw.dao.CartDAO;
import com.bookstore_csa_cw.dao.CustomerDAO;
import com.bookstore_csa_cw.dao.OrderDAO;
import com.bookstore_csa_cw.exception.CartNotFoundException;
import com.bookstore_csa_cw.exception.CustomerNotFoundException;
import com.bookstore_csa_cw.model.Book;
import com.bookstore_csa_cw.model.Cart;
import com.bookstore_csa_cw.model.Order;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    
    private static final Logger logger = Logger.getLogger(OrderResource.class.getName());

    // Place a new order
    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        // Validate Customer existence
        if (CustomerDAO.getCustomerById(customerId) == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        // Validate Cart existence and not empty
        Cart cart = CartDAO.getCart(customerId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new CartNotFoundException("Cart not found or is empty. Cannot place an order.");
        }

        Map<Integer, Integer> cartItems = cart.getItems();
        Map<Integer, Integer> orderItems = new HashMap<>();
        double totalPrice = 0.0;

        // Calculate total and copy cart items
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int bookId = entry.getKey();
            int quantity = entry.getValue();

            Book book = BookDAO.getBookById(bookId);
            if (book != null) {
                orderItems.put(bookId, quantity);
                totalPrice += book.getPrice() * quantity;
            }
        }

        // Create the order
        Order order = OrderDAO.createOrder(customerId, orderItems, totalPrice);

        // Clear customer's cart after placing order
        CartDAO.deleteCart(customerId);

        logger.log(Level.INFO, "Order placed successfully: Order ID = " + order.getOrderId() + ", Customer ID = " + customerId);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    //Get all orders for a customer
    @GET
    public Response getAllOrders(@PathParam("customerId") int customerId) {
        if (CustomerDAO.getCustomerById(customerId) == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        List<Order> orders = OrderDAO.getOrdersByCustomer(customerId);
        return Response.ok(orders).build();
    }

    //Get a specific order
    @GET
    @Path("/{orderId}")
    public Response getSpecificOrder(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        if (CustomerDAO.getCustomerById(customerId) == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }

        Order order = OrderDAO.getSpecificOrder(customerId, orderId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\": \"Order with ID " + orderId + " not found for customer " + customerId + ".\"}")
                .build();
        }

        return Response.ok(order).build();
    }
}
