package dao;

import java.util.List;

import entities.Order;

public interface OrderDAO {
    public List<Order> getOrders();
    public Order getOrder(int id);
    public int getCountOrder();
    public double getSumOrder();
    public long createOrder(Order order);
    public long updateOrder(Order order, int id);
    public long deleteOrder(int id);
}


