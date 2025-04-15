package dao;

import exception.OrderNotFoundException;
import exception.DatabaseConnectionException;
import util.DBConnUtil;

import java.sql.*;

public class OrderService implements IOrderService {
    private Connection con;

    public OrderService() throws DatabaseConnectionException {
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public double calculateTotalAmount(int orderId) throws OrderNotFoundException {
        double totalAmount = 0;

        String query = "SELECT od.Quantity, p.Price " +
                       "FROM orderdetails od " +
                       "JOIN products p ON od.ProductID = p.ProductID " +
                       "WHERE od.OrderID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                totalAmount += quantity * price;
            }

            if (!found) {
                throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalAmount;
    }

    @Override
    public void getOrderDetails(int orderId) throws OrderNotFoundException {
        String query = "SELECT p.ProductName, od.Quantity " +
                       "FROM orderdetails od " +
                       "JOIN products p ON od.ProductID = p.ProductID " +
                       "WHERE od.OrderID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            System.out.println("Order Details for Order ID " + orderId + ":");

            while (rs.next()) {
                found = true;
                String productName = rs.getString("ProductName");
                int qty = rs.getInt("Quantity");
                System.out.println("Product: " + productName + " | Quantity: " + qty);
            }

            if (!found) {
                throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) throws OrderNotFoundException {
        boolean updated = false;

        String query = "UPDATE orders SET Status = ? WHERE OrderId = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                updated = true;
            } else {
                throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public boolean cancelOrder(int orderId) throws OrderNotFoundException {
        boolean cancelled = false;

        try {
            // Step 1: Get productId and quantity for the order
            String getDetails = "SELECT ProductID, Quantity FROM orderdetails WHERE OrderId = ?";
            try (PreparedStatement ps = con.prepareStatement(getDetails)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();

                boolean found = false;

                while (rs.next()) {
                    found = true;
                    int productId = rs.getInt("ProductID");
                    int qty = rs.getInt("Quantity");

                    // Step 2: Update inventory stock
                    String updateStock = "UPDATE inventory SET QuantityInStock = QuantityInStock + ? WHERE ProductID = ?";
                    try (PreparedStatement updatePs = con.prepareStatement(updateStock)) {
                        updatePs.setInt(1, qty);
                        updatePs.setInt(2, productId);
                        updatePs.executeUpdate();
                    }
                }

                if (!found) {
                    throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
                }
            }

            // Step 3: Delete from orderdetails
            String deleteDetails = "DELETE FROM orderdetails WHERE OrderId = ?";
            try (PreparedStatement ps = con.prepareStatement(deleteDetails)) {
                ps.setInt(1, orderId);
                ps.executeUpdate();
            }

            // Step 4: Delete from orders
            String deleteOrder = "DELETE FROM orders WHERE OrderId = ?";
            try (PreparedStatement ps = con.prepareStatement(deleteOrder)) {
                ps.setInt(1, orderId);
                int rows = ps.executeUpdate();
                cancelled = rows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cancelled;
    }
}
