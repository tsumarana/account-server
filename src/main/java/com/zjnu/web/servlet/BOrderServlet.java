package com.zjnu.web.servlet;

import com.zjnu.pojo.Goods;
import com.zjnu.pojo.Order;
import com.zjnu.service.BOrderService;
import com.zjnu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("border")
public class BOrderServlet<List> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    BOrderService bOrderService;
    @Autowired
    GoodsService goodsService;
    @RequestMapping("addOrder")
    public Order addOrder(@RequestBody Goods goods){
        Order order = new Order();
        order.setBrandId(String.valueOf(goods.getId()));
        order.setBuyer(goods.getUsername());
        order.setImg(goods.getImg());
        order.setBrandId(String.valueOf(goods.getId()));
        order.setSeller(goods.getSeller());
        order.setName(goods.getTitle());
        order.setTime(sdf.format(new Date()));
        order.setStatus("ing");
        order.setBuyer(goods.getUsername());
        order.setPrice(String.valueOf(goods.getPrice()));
        String re_id = String.valueOf(UUID.randomUUID());
        order.setRe_id(re_id);
        bOrderService.addOrder(order);
        return bOrderService.selectByReId(order);
    }

    @RequestMapping("selectBuyerOrder")
    public java.util.List<Order> selectBuyerOrder(@RequestBody Order order){
        System.out.println(order);
        java.util.List<Order> orders = bOrderService.selectBuyerOrder(order);
        for (Order order1:orders){
            if(order1.getStatus().equals("ing")){
                order1.setStatus("未支付");
            }else if(order1.getStatus().equals("pay")){
                order1.setStatus("待确认");
            }
        }
        return orders;
    }
    @RequestMapping("pay")
    public String pay(@RequestBody Order order){
        bOrderService.pay(order);
        order = bOrderService.selectOrderById(order);
        Goods goods = new Goods();
        goods.setId(Integer.valueOf(order.getBrandId()));
        goodsService.alterByPay(goods);
        return "success";
    }
}
