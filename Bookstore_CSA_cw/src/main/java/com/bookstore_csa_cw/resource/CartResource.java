package com.bookstore_csa_cw.resource;

import com.bookstore_csa_cw.dao.BookDAO;
import com.bookstore_csa_cw.dao.CartDAO;
import com.bookstore_csa_cw.dao.CustomerDAO;
import com.bookstore_csa_cw.model.Book;
import com.bookstore_csa_cw.model.Cart;
import com.bookstore_csa_cw.exception.BookNotFoundException;
import com.bookstore_csa_cw.exception.CartNotFoundException;
import com.bookstore_csa_cw.exception.OutOfStockException;
import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    
    private static final Logger logger = Logger.getLogger(CartResource.class.getName());

    @POST
    @Path("/items")
    public Response addItems(@PathParam("customerId") int customerId, Map<String, Integer> item) {
        Integer bookId = item.get("bookId");
        Integer quantity = item.get("quantity");
        
        if (bookId == null || quantity == null || quantity <= 0) {
            throw new InvalidInputException("bookId and quantity must be provided and quantity must be greater than 0.");
        }

        Book book = BookDAO.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");
        }

        if (book.getStockQuantity() < quantity) {
            throw new OutOfStockException("Not enough stock for book ID " + bookId + ".");
        }
       
        Cart cart = CartDAO.getCart(customerId);
        if (cart == null) {
            cart = new Cart();            
            CartDAO.addCart(customerId, cart);
            cart = CartDAO.getCart(customerId);
        }
                
        if (CartDAO.checkCustomerCart(customerId, bookId)) {
            throw new InvalidInputException("Item is already in the cart");
        }
        cart.addItem(bookId, quantity);
        
        // Stock update
        book.setStockQuantity(book.getStockQuantity() - quantity);
        
        logger.log(Level.INFO, "Book ID " + bookId + " added to customer " + customerId + "'s cart.");
        return Response.status(Response.Status.CREATED).entity("{\"message\": \"Book added to cart.\"}").build();
    }
    
    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        
        Cart cart = CartDAO.getCart(customerId);
        Customer customer = CustomerDAO.getCustomerById(customerId);
        
        //If customer is valid but cart is empty, respnd with 200 OK status code
        if (cart == null && customer != null ){
            return Response.ok("{\"message\": \"Cart is empty.\"}").build();
        }
        
        // If no cart found for this customer
        if (cart == null) {
            throw new CartNotFoundException("Cart is empty or does not exist for customer ID " + customerId + ".");
        }

        Map<Integer, Integer> items = cart.getItems();

        // If cart exists but has no items
        if (items == null || items.isEmpty()) {
            return Response.ok("{\"message\": \"Cart is empty.\"}").build();
        }
        
        // List of item details
        List<Map<String, Integer>> responseBody = new ArrayList<>();
        
        //Format the response body
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Map<String, Integer> itemDetails = new HashMap<>();
            itemDetails.put("quantity", entry.getValue());
            itemDetails.put("bookId", entry.getKey());
            responseBody.add(itemDetails);
        }

    return Response.ok(responseBody).build();
    }
    
    @PUT
    @Path("/items/{bookId}")
    public Response updateItem(@PathParam("customerId") int customerId, 
                               @PathParam("bookId") int bookId, 
                               Map<String, Integer> quantityRequest) {
        
        Integer newQuantity = quantityRequest.get("quantity");
        if (newQuantity == null || newQuantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0.");
        }
        
        Cart cart = CartDAO.getCart(customerId);
        if (cart == null || !cart.getItems().containsKey(bookId)) {
            throw new CartNotFoundException("Book with ID " + bookId + " not found in customer's cart.");
        }
        
        Book book = BookDAO.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " does not exist.");
        }
        
        int currentQuantity = cart.getItems().get(bookId);
        int difference = newQuantity - currentQuantity;

        if (difference > 0 && book.getStockQuantity() < difference) {
            throw new OutOfStockException("Not enough stock to update quantity for book ID " + bookId + ".");
        }  

        // Adjust the stock
        book.setStockQuantity(book.getStockQuantity() - difference);
        cart.updateItem(bookId, newQuantity);

        logger.log(Level.INFO, "Book ID " + bookId + " updated in customer " + customerId + "'s cart.");
        return Response.ok("{\"message\": \"Cart item updated.\"}").build();
    }
    
    @DELETE
    @Path("/items/{bookId}")
    public Response removeItem(@PathParam("customerId") int customerId, 
                                @PathParam("bookId") int bookId) {

        Cart cart = CartDAO.getCart(customerId);
        if (cart == null || !cart.getItems().containsKey(bookId)) {
            throw new CartNotFoundException("Book with ID " + bookId + " not found in customer's cart.");
        }

        int restoringQuantity = cart.getItems().get(bookId);
        
        boolean removed = cart.removeItem(bookId);
        if(!removed){
            throw new BookNotFoundException("Book with ID " + bookId + " not found in the cart."); 
        }

        Book book = BookDAO.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");            
        }
        book.setStockQuantity(book.getStockQuantity() + restoringQuantity);
        logger.log(Level.WARNING, "Book ID " + bookId + " removed from customer " + customerId + "'s cart.");
        return Response.noContent().build();
    }
}
