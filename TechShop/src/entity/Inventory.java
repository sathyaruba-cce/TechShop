package entity;
import java.util.Date;
public class Inventory {
	 private int inventoryID;
	    private Product product;  
	    private int quantityInStock;
	    private Date lastStockUpdate;
	    public Inventory() {
	    	
	    }
		public int getInventoryID() {
			return inventoryID;
		}
		public void setInventoryID(int inventoryID) {
			this.inventoryID = inventoryID;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public int getQuantityInStock() {
			return quantityInStock;
		}
		public void setQuantityInStock(int quantityInStock) {
			this.quantityInStock = quantityInStock;
		}
		public Date getLastStockUpdate() {
			return lastStockUpdate;
		}
		public void setLastStockUpdate(Date lastStockUpdate) {
			this.lastStockUpdate = lastStockUpdate;
		}
		public Inventory(int inventoryID, Product product, int quantityInStock, Date lastStockUpdate) {
			super();
			this.inventoryID = inventoryID;
			this.product = product;
			this.quantityInStock = quantityInStock;
			this.lastStockUpdate = lastStockUpdate;
		}
		 @Override
		    public String toString() {
		        return "InventoryID: " + inventoryID +
		               ", Product: " + product +
		               ", Quantity In Stock: " + quantityInStock +
		               ", Last Stock Update: " + lastStockUpdate;
		    }
}
