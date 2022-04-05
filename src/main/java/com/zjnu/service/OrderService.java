package com.zjnu.service;

import com.zjnu.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> selectSellerOrder(Order order);
    void deleteOrderByUsername(String username);
    void freezeUser(String username);
    void deleteOrderById(int id);
}
