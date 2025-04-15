
package dao;

import exception.OrderNotFoundException;

public interface IOrderService {
    double calculateTotalAmount(int orderId) throws OrderNotFoundException;
    void getOrderDetails(int orderId) throws OrderNotFoundException;
    boolean updateOrderStatus(int orderId, String status) throws OrderNotFoundException;
    boolean cancelOrder(int orderId) throws OrderNotFoundException;
}
