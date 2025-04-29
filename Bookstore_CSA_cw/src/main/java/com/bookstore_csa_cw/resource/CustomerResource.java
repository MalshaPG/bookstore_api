package com.bookstore_csa_cw.resource;

import com.bookstore_csa_cw.dao.CustomerDAO;
import com.bookstore_csa_cw.model.Customer;
import com.bookstore_csa_cw.exception.CustomerNotFoundException;
import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.validations.CustomerValidations;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static final Logger logger = Logger.getLogger(CustomerResource.class.getName());    
   
    // Add new customer
    @POST
    public Response addNewCustomer(Customer customer) {
        // Input validation
        CustomerValidations.validateCustomer(customer, 
                "First name, last name, email, and password are required.");

        CustomerDAO.addCustomer(customer);
        logger.log(Level.INFO, "Customer added:"+ customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    // Get all customers
    @GET
    public Response getAllCustomers() {
        return Response.ok(CustomerDAO.getAllCustomers()).build();
    }

    // Get customer by customerId
    @GET
    @Path("/{customerId}")
    public Response getCustomerById(@PathParam("customerId") int id) {
        Customer customer = CustomerDAO.getCustomerById(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        return Response.ok(customer).build();
    }

    // Update a customer's details
    @PUT
    @Path("/{customerId}")
    public Response updateCustomerDetails(@PathParam("customerId") int customerId, Customer updatingCustomer) {
        // Input validation for updates
        CustomerValidations.validateCustomer(updatingCustomer, 
                "First name, last name, email, and password must not be empty for update.");

        boolean updated = CustomerDAO.updateCustomer(customerId, updatingCustomer);
        if (updated) {
            logger.log(Level.INFO, "Customer updated at customerId: " + customerId);
            return Response.ok(CustomerDAO.getCustomerById(customerId)).build();
        }
        throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
    }

    // Delete a customer
    @DELETE
    @Path("/{customerId}")
    public Response deleteCustomer(@PathParam("customerId") int id) {
        boolean deleted = CustomerDAO.deleteCustomer(id);
        if (!deleted) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        logger.log(Level.WARNING, "Customer deleted with ID: " + id);
        return Response.noContent().build();
    }
}
