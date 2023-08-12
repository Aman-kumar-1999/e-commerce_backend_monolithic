package com.eqipped.service.impl;

import com.eqipped.entities.Order;
import com.eqipped.helper.OrderStatusRequest;
import com.eqipped.repositories.OrderRepository;
import com.eqipped.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WhatsApps whatsApps;


    @Override
    public List<Order> getAllOrder() {
        List li = orderRepository.findAll();
        return li;
    }

    @Override
    public Order getOrder(String orderId) {
        Order order = orderRepository.findById(orderId).get();
        return order;

    }
    @Override
    public List<Order> getVendorOrder(String vendorId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("vendorId").is(vendorId));
        List<Order> order = mongoTemplate.find(query, Order.class);
        return order;
    }

    @Override
    public List<Order> getStatusVendorOrder(OrderStatusRequest orderStatusRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(orderStatusRequest.getStatus()).and("vendorId").is(orderStatusRequest.getVendorId()));
        List<Order> order = mongoTemplate.find(query, Order.class);
        return order;
    }
    @Override
    public List<Order> getStatusOrder(OrderStatusRequest orderStatusRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(orderStatusRequest.getStatus()));
        List<Order> order = mongoTemplate.find(query, Order.class);
        return order;
    }

    public List<Order> getOrderByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Order> order = mongoTemplate.find(query, Order.class);
        return order;
    }

    @Override
    public Order createOrder(Order order) {

        order.setOrderId(whatsApps.getAlphaNumericString(6));
        order.setDate(LocalDate.now());
        Order oldOrder = orderRepository.save(order);
        return oldOrder;
    }

    @Override
    public Order updateOrder(Order order) {
        Order order1 = orderRepository.findById(order.getOrderId()).get();
        if(order1 !=null) {
            order1 = order;
            order1.setDate(LocalDate.now());
            orderRepository.save(order1);
        }
        return order1;
    }

    @Override
    public void deleteOrder(String orderId) {
        try{
            orderRepository.deleteById(orderId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
