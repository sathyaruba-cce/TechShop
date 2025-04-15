package entity;

public class OrderDetails {
	private int orderDetailID;
    private Orders order;     
    private Product product;  
    private int quantity;
    public OrderDetails() {
    	
    }
	public int getOrderDetailID() {
		return orderDetailID;
	}
	public void setOrderDetailID(int orderDetailID) {
		this.orderDetailID = orderDetailID;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public OrderDetails(int orderDetailID, Orders order, Product product, int quantity) {
		super();
		this.orderDetailID = orderDetailID;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}
	@Override
    public String toString() {
        return "OrderDetailID: " + orderDetailID + 
               ", Order: " + order + 
               ", Product: " + product + 
               ", Quantity: " + quantity;
    }
}
