package com.zjnu.service;

import com.zjnu.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BOrderService {
     void addOrder(Order order);
     List<Order> selectBuyerOrder(Order order);
     void pay(Order order);
     Order selectByReId(Order order);
     Order selectOrderById(Order order);
     List<Order> selectOrderByUser(Order order);
     void confirm(Order order);
     void cancel(Order order);
}

