package com.zjnu.service.impl;

import com.zjnu.mapper.bOrderMapper;
import com.zjnu.pojo.Order;
import com.zjnu.service.BOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BOrderServiceImpl implements BOrderService {
    @Autowired
    bOrderMapper mapper;
    @Override
    public void addOrder(Order order) {
        mapper.addOrder(order);
    }

    @Override
    public List<Order> selectBuyerOrder(Order order) {
        List<Order> orders = mapper.selectBuyerOrder(order);
        return orders;
    }

    @Override
    public void pay(Order order) {
        mapper.pay(order);
    }


}
