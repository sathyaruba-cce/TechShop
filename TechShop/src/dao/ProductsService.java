package dao;
import entity.Product;
import exception.DatabaseConnectionException;
import exception.ProductNotFoundException;
import util.DBConnUtil;

import java.sql.*;
public class ProductsService implements IProductsService {
	private Connection con;
	public ProductsService() throws DatabaseConnectionException {
        super();
        con = DBConnUtil.getDbConnection();
    }
	 @Override
	    public Product getProductDetails(int productId) throws ProductNotFoundException {
	        Product product = null;
	        try {
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE ProductID = ?");
	            ps.setInt(1, productId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                product = new Product(
	                        rs.getInt("ProductID"),
	                        rs.getString("ProductName"),
	                        rs.getString("Description"),
	                        rs.getDouble("Price")
	                );
	            } else {
	                throw new ProductNotFoundException("Product with ID " + productId + " not found.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return product;
	    }

	    @Override
	    public boolean updateProductInfo(int productId, int option, String newValue) throws ProductNotFoundException {
	        boolean updated = false;
	        String sql;

	        switch (option) {
	            case 1 -> sql = "UPDATE products SET Price = ? WHERE ProductID = ?";
	            case 2 -> sql = "UPDATE products SET Description = ? WHERE ProductID = ?";
	            default -> throw new IllegalArgumentException("Invalid option selected.");
	        }

	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            if (option == 1) {
	                ps.setDouble(1, Double.parseDouble(newValue));
	            } else {
	                ps.setString(1, newValue);
	            }
	            ps.setInt(2, productId);

	            int rows = ps.executeUpdate();
	            updated = rows > 0;

	            if (!updated) {
	                throw new ProductNotFoundException("Product with ID " + productId + " not found for update.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return updated;
	    }

	    @Override
	    public boolean isProductInStock(int productId) throws ProductNotFoundException {
	        try {
	            PreparedStatement ps = con.prepareStatement("SELECT QuantityInStock FROM inventory WHERE ProductID = ?");
	            ps.setInt(1, productId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                return rs.getInt("QuantityInStock") > 0;
	            } else {
	                throw new ProductNotFoundException("Product with ID " + productId + " not found in inventory.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
}
