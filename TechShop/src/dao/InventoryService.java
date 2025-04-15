package dao;

import entity.Inventory;
import entity.Product;
import util.DBConnUtil;
import exception.DatabaseConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService implements IInventoryService {

    private Connection con;

    public InventoryService() throws DatabaseConnectionException {
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public void addToInventory(int inventoryID, int quantity) {
        try {
            String updateQuery = "UPDATE inventory SET QuantityInStock = QuantityInStock + ? WHERE InventoryID = ?";
            try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, inventoryID);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Added " + quantity + " to Inventory ID: " + inventoryID);
                } else {
                    System.out.println("Inventory ID not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromInventory(int inventoryID, int quantity) {
        try {
            String updateQuery = "UPDATE inventory SET QuantityInStock = QuantityInStock - ? WHERE InventoryID = ?";
            try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, inventoryID);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Removed " + quantity + " from Inventory ID: " + inventoryID);
                } else {
                    System.out.println("Inventory ID not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStockQuantity(int inventoryID, int newQuantity) {
        try {
            String updateQuery = "UPDATE inventory SET QuantityInStock = ? WHERE InventoryID = ?";
            try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
                stmt.setInt(1, newQuantity);
                stmt.setInt(2, inventoryID);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Updated stock quantity for Inventory ID: " + inventoryID);
                } else {
                    System.out.println("Inventory ID not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isProductAvailable(int inventoryID, int quantityToCheck) {
        try {
            String query = "SELECT QuantityInStock FROM inventory WHERE InventoryID = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, inventoryID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int currentStock = rs.getInt("QuantityInStock");
                    return currentStock >= quantityToCheck;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getInventoryValue(int inventoryID) {
        try {
            String query = "SELECT QuantityInStock, products.Price FROM inventory " +
                    "JOIN products ON inventory.ProductID = products.ProductID WHERE InventoryID = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, inventoryID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int quantityInStock = rs.getInt("QuantityInStock");
                    double productPrice = rs.getDouble("Price");
                    return quantityInStock * productPrice;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Inventory> listLowStockProducts(int threshold) {
        List<Inventory> lowStockProducts = new ArrayList<>();
        try {
            String query = "SELECT * FROM inventory WHERE QuantityInStock < ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, threshold);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int inventoryID = rs.getInt("InventoryID");
                    int productID = rs.getInt("ProductID");
                    int quantityInStock = rs.getInt("QuantityInStock");
                    Date lastStockUpdate = rs.getDate("LastStockUpdate");
                    Product product = getProductById(productID);
                    Inventory inventory = new Inventory(inventoryID, product, quantityInStock, lastStockUpdate);
                    lowStockProducts.add(inventory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowStockProducts;
    }

    @Override
    public List<Inventory> listOutOfStockProducts() {
        List<Inventory> outOfStockProducts = new ArrayList<>();
        try {
            String query = "SELECT * FROM inventory WHERE QuantityInStock = 0";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int inventoryID = rs.getInt("InventoryID");
                    int productID = rs.getInt("ProductID");
                    int quantityInStock = rs.getInt("QuantityInStock");
                    Date lastStockUpdate = rs.getDate("LastStockUpdate");
                    Product product = getProductById(productID);
                    Inventory inventory = new Inventory(inventoryID, product, quantityInStock, lastStockUpdate);
                    outOfStockProducts.add(inventory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outOfStockProducts;
    }

    @Override
    public void listAllProducts() {
        try {
            String query = "SELECT * FROM inventory";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                System.out.println("All Products in Inventory:");
                System.out.println("---------------------------------------------------");
                while (rs.next()) {
                    int inventoryID = rs.getInt("InventoryID");
                    int productID = rs.getInt("ProductID");
                    int quantityInStock = rs.getInt("QuantityInStock");
                    Date lastStockUpdate = rs.getDate("LastStockUpdate");

                    Product product = getProductById(productID);
                    System.out.println(String.format("InventoryID: %-5d Product: %-20s Quantity: %-5d Last Update: %-15s",
                            inventoryID, product.getProductName(), quantityInStock, lastStockUpdate));
                }
                System.out.println("---------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductById(int productID) {
        try {
            String query = "SELECT * FROM products WHERE ProductID = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, productID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("ProductName");
                    double price = rs.getDouble("Price");
                    
                    return new Product(productID, productName, price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
