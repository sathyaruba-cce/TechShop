package dao;

import entity.Orders;
import entity.Product;
import entity.OrderDetails;
import exception.DatabaseConnectionException;
import exception.OrderDetailNotFoundException;
import util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsImplementation implements IOrderDetails {
    private Connection con;

    public OrderDetailsImplementation() throws DatabaseConnectionException {
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public double calculateSubtotal(OrderDetails orderDetails) {
        return orderDetails.getQuantity() * orderDetails.getProduct().getPrice();
    }

    @Override
    public void getOrderDetailInfo(OrderDetails orderDetails) {
        System.out.println("Order Detail ID: " + orderDetails.getOrderDetailID());
        System.out.println("Order ID: " + orderDetails.getOrder().getOrderId());
        System.out.println("Product: " + orderDetails.getProduct().getProductName());
        System.out.println("Quantity: " + orderDetails.getQuantity());
        System.out.println("Subtotal: ₹" + calculateSubtotal(orderDetails));
    }

    @Override
    public void updateQuantity(OrderDetails orderDetails, int newQuantity) {
        String updateQuery = "UPDATE orderdetails SET Quantity = ? WHERE OrderDetailID = ?";
        try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, orderDetails.getOrderDetailID());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                orderDetails.setQuantity(newQuantity);
                System.out.println("Quantity updated successfully.");
            } else {
                System.out.println("Order detail not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDiscount(OrderDetails orderDetails, double discountPercentage) {
        double price = orderDetails.getProduct().getPrice();
        double discountedPrice = price - (price * discountPercentage / 100);
        orderDetails.getProduct().setPrice(discountedPrice);
        System.out.println("Discount applied: " + discountPercentage + "%");
        System.out.println("New Price: ₹" + discountedPrice);
    }

    @Override
    public OrderDetails fetchOrderDetailById(int orderDetailId) throws OrderDetailNotFoundException {
        String query = "SELECT od.OrderDetailID, od.Quantity, " +
                       "o.OrderId, o.OrderDate, o.Status, " +
                       "p.ProductID, p.ProductName, p.Description, p.Price " +
                       "FROM orderdetails od " +
                       "JOIN orders o ON od.OrderID = o.OrderId " +
                       "JOIN products p ON od.ProductID = p.ProductID " +
                       "WHERE od.OrderDetailID = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderDetailId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("OrderId"));
                order.setOrderDate(rs.getDate("OrderDate").toLocalDate());
                order.setStatus(rs.getString("Status"));

                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));

                OrderDetails details = new OrderDetails();
                details.setOrderDetailID(rs.getInt("OrderDetailID"));
                details.setOrder(order);
                details.setProduct(product);
                details.setQuantity(rs.getInt("Quantity"));

                return details;
            } else {
                throw new OrderDetailNotFoundException("OrderDetail with ID " + orderDetailId + " not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderDetailNotFoundException("Database error occurred while fetching order detail.");
        }
    }
}
