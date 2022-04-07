package com.zjnu.service.impl;

import com.zjnu.mapper.OrderMapper;
import com.zjnu.pojo.Order;
import com.zjnu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper mapper;
    @Override
    public List<Order> selectSellerOrder(Order order) {
        List<Order> orders = mapper.selectSellerOrder(order);
        return orders;
    }

    @Override
    public void deleteOrderByUsername(String username) {
        mapper.deleteOrderByUsername(username);
    }

    @Override
    public void freezeUser(String username) {
        mapper.freezeUser(username);
    }

    @Override
    public void deleteOrderById(int id) {
        mapper.deleteOrderById(id);
    }

    @Override
    public void addOrder(Order order) {
        mapper.addOrder(order);
    }

    @Override
    public void confirm(Order order) {
        mapper.confirm(order);
    }


}
