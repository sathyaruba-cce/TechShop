
package dao;

import entity.Inventory;
import java.util.List;

public interface IInventoryService {
    void addToInventory(int inventoryID, int quantity);
    void removeFromInventory(int inventoryID, int quantity);
    void updateStockQuantity(int inventoryID, int newQuantity);
    boolean isProductAvailable(int inventoryID, int quantityToCheck);
    double getInventoryValue(int inventoryID);
    List<Inventory> listLowStockProducts(int threshold);
    List<Inventory> listOutOfStockProducts();
    void listAllProducts();
}

