package dao;

import entity.Customer;
import entity.Product;
import exception.CustomerNotFoundException;
import exception.DatabaseConnectionException;
import exception.InvalidDataException;
import util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService {

	private Connection con;
	public CustomerService() throws DatabaseConnectionException {
        super();
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public boolean insertCustomer(Customer customer) throws InvalidDataException {
        validateCustomer(customer);
        boolean inserted = false;
        try {
            PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO customers (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());

            int rows = pstmt.executeUpdate();
            inserted = rows > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting customer: " + e.getMessage());
        }
        return inserted;
    }

    @Override
    public boolean updateCustomerInfo(int customerId, int fieldToUpdate, String newValue)
            throws CustomerNotFoundException, InvalidDataException {

        String sql = "";
        switch (fieldToUpdate) {
            case 1:
                if (!newValue.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                    throw new InvalidDataException("Invalid email format.");
                }
                sql = "UPDATE customers SET Email = ? WHERE CustomerId= ?";
                break;

            case 2:
                if (!newValue.matches("^[0-9]{10}$")) {
                    throw new InvalidDataException("Phone number must be 10 digits.");
                }
                sql = "UPDATE customers SET Phone = ? WHERE CustomerId = ?";
                break;

            case 3:
                sql = "UPDATE customers SET Address = ? WHERE CustomerId = ?";
                break;

            default:
                throw new InvalidDataException("Invalid field. Choose only from: email, phone, or address.");
        }

        boolean updated = false;

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setInt(2, customerId);

            int rows = pstmt.executeUpdate();

            if (rows == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }

            updated = true;

        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }

        return updated;
    }

    @Override
    public int calculateTotalOrders(int customerId) throws CustomerNotFoundException {
        int totalOrders = 0;

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(*) FROM orders WHERE CustomerId = ?");
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalOrders = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching total orders: " + e.getMessage());
        }

        if (totalOrders == 0) {
            throw new CustomerNotFoundException("No orders found for customer ID: " + customerId);
        }

        return totalOrders;
    }

    @Override
    public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
        Customer customer = null;

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM customers WHERE CustomerId = ?");
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
            	customer = new Customer(
            		    rs.getInt("CustomerId"),
            		    rs.getString("FirstName"),
            		    rs.getString("LastName"),
            		    rs.getString("Email"),
            		    rs.getString("Phone"),
            		    rs.getString("Address")
            		);

            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching customer: " + e.getMessage());
        }

        return customer;
    }

    private void validateCustomer(Customer customer) throws InvalidDataException {
        if (!customer.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new InvalidDataException("Invalid email format.");
        }
        if (!customer.getPhone().matches("^[0-9]{10}$")) {
            throw new InvalidDataException("Phone number must be 10 digits.");
        }
        
    }
    @Override
    public List<Product> getAvailableProducts() {
        List<Product> availableProducts = new ArrayList<>();

        try {
            
            String sql = "SELECT * FROM products WHERE quantityAvailable > 0";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("ProductID"),          
                    rs.getString("ProductName"),      
                    rs.getString("Description"),      
                    rs.getDouble("Price")             
                );
                availableProducts.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching available products: " + e.getMessage());
        }

        return availableProducts;
    } 
}
