package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.OrderRepostitory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    public final OrderRepostitory orderRepostitory;

    public OrderService(OrderRepostitory orderRepostitory) {
        this.orderRepostitory = orderRepostitory;
    }

    public List<Order> getAllOrders(){
       return orderRepostitory.findAll();
    }

    public Order getOrderById(int id){
        Optional<Order> optionalOrder=orderRepostitory.findById(id);
        return optionalOrder.orElse(null);
    }

    @Transactional
    public void updateOrder (int id, Order order) {
        order.setId(id);
        orderRepostitory.save(order);
    }

}
