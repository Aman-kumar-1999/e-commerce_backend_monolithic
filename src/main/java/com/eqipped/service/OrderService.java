package com.eqipped.service;

import com.eqipped.entities.Order;
import com.eqipped.helper.OrderStatusRequest;

import java.util.List;

public interface OrderService {


    // get all order
    public List<Order> getAllOrder();

    // get order by id
    public Order getOrder(String orderId);

    // get Orders on the behalf of status (confirmed , pending , Cancelled) and vendorId
    public List<Order> getStatusVendorOrder(OrderStatusRequest orderStatusRequest);
    public List<Order> getVendorOrder(String vendorId);


    // get Orders on the behalf of status (confirmed , pending , Cancelled) only
    public List<Order> getStatusOrder(OrderStatusRequest orderStatusRequest);

    // Order through email
    public List<Order> getOrderByEmail(String email);

    // create order
    public Order createOrder(Order order);

    // update order
    public Order updateOrder(Order order);

    // delete order
    public void deleteOrder(String orderId);


}
