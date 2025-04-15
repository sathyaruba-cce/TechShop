package dao;
import entity.OrderDetails;
import exception.OrderDetailNotFoundException;

public interface IOrderDetails {
	 double calculateSubtotal(OrderDetails orderDetails);
	    void getOrderDetailInfo(OrderDetails orderDetails);
	    void updateQuantity(OrderDetails orderDetails, int newQuantity);
	    void addDiscount(OrderDetails orderDetails, double discountPercentage);
	    OrderDetails fetchOrderDetailById(int orderDetailId) throws OrderDetailNotFoundException;

}
