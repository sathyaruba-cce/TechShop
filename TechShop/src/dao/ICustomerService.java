package dao;

import java.util.List;

import entity.Customer;
import exception.CustomerNotFoundException;
import exception.InvalidDataException;
import entity.Product;
public interface ICustomerService {
    boolean insertCustomer(Customer customer) throws InvalidDataException;

    boolean updateCustomerInfo(int customerId, int fieldToUpdate, String newValue)
            throws CustomerNotFoundException, InvalidDataException;

    int calculateTotalOrders(int customerId) throws CustomerNotFoundException;

    Customer getCustomerById(int customerId) throws CustomerNotFoundException;
    List<Product> getAvailableProducts();
}