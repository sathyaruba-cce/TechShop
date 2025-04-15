package dao;
import entity.Product;
import exception.ProductNotFoundException;
public interface IProductsService {
	    Product getProductDetails(int productId) throws ProductNotFoundException;
	    boolean updateProductInfo(int productId, int option, String newValue) throws ProductNotFoundException;
	    boolean isProductInStock(int productId) throws ProductNotFoundException;
	}

