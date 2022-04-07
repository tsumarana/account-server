package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Goods;
import com.zjnu.pojo.Order;
import com.zjnu.service.GoodsService;
import com.zjnu.service.OrderService;
import com.zjnu.service.TrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderServlet {
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    TrolleyService trolleyService;
    @RequestMapping("/selectSellerOrder")
    public void selectSellerOrder(HttpServletRequest req , HttpServletResponse resp)throws IOException {
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        Order order = JSON.parseObject(s, Order.class);
        System.out.println(order);
        List<Order> orders = orderService.selectSellerOrder(order);
        for(Order order1:orders){
            if(order1.getStatus().equals("true")){
                order1.setStatus("正常");
            }else if(order1.getStatus().equals("finish")){
                order1.setStatus("完成");
            }
            else{
                order1.setStatus("冻结");
            }
        }
        resp.setContentType("text/json;charset=utf-8");
        String jsonString = JSON.toJSONString(orders);
        resp.getWriter().write(jsonString);
    }
    @RequestMapping("/deleteOrder")
    public void deleteOrder(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        Goods goods = JSON.parseObject(s, Goods.class);
        System.out.println(goods);
        int id = goods.getId();
        orderService.deleteOrderById(id);
        goodsService.deleteGoodsById(id);
        trolleyService.deleteTrolleyById(id);
    }
    @RequestMapping("/delete")
    public void delete(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        Order order = JSON.parseObject(s, Order.class);
        System.out.println(order);
        int id = Integer.parseInt(order.getBrandId());
        orderService.deleteOrderById(id);
        goodsService.deleteGoodsById(id);
        trolleyService.deleteTrolleyById(id);
    }
}
