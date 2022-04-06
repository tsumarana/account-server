package com.zjnu.service;

import com.zjnu.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BOrderService {
    public void addOrder(Order order);
    public List<Order> selectBuyerOrder(Order order);
    public void pay(Order order);
}
