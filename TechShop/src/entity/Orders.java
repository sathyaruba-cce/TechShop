package entity;
import java.time.LocalDate;
public class Orders {
	 private int orderId;
	    private Customer customer; 
	    private LocalDate orderDate;
	    private double totalAmount;
	    private String status;
	    public Orders() {
	    }
		public Orders(int orderId, Customer customer, LocalDate orderDate, double totalAmount, String status) {
			super();
			this.orderId = orderId;
			this.customer = customer;
			this.orderDate = orderDate;
			this.totalAmount = totalAmount;
			this.status = status; 
		}
		public int getOrderId() {
			return orderId;
		}
		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		public LocalDate getOrderDate() {
			return orderDate;
		}
		public void setOrderDate(LocalDate orderDate) {
			this.orderDate = orderDate;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }
		 @Override
		    public String toString() {
		        return "Order {" +
		                "ID=" + orderId +
		                ", Customer=" + (customer != null ? customer.getFirstName() + " " + customer.getLastName() : "N/A") +
		                ", Order Date=" + orderDate +
		                ", Total Amount=" + totalAmount +
		                '}';
		    }
}

