package com.zjnu.web.servlet;

import com.zjnu.pojo.Friend;
import com.zjnu.pojo.Goods;
import com.zjnu.pojo.Order;
import com.zjnu.pojo.User;
import com.zjnu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("border")
public class BOrderServlet<List> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    BOrderService bOrderService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    FriendService friendService;
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
            }else if(order1.getStatus().equals("finish")){
                order1.setStatus("完成");
            }
        }
        return orders;
    }
    @RequestMapping("pay")
    public Order pay(@RequestBody Order order){
        //根据id修改订单状态
        bOrderService.pay(order);
        //根据id查询订单
        order = bOrderService.selectOrderById(order);
        Goods goods = new Goods();
        goods.setId(Integer.valueOf(order.getBrandId()));
        goodsService.alterByPay(goods);
        return order;
    }

    @RequestMapping("selectOrderByUser")
    public java.util.List<Order> selectOrderByUser(@RequestBody Order order){
        return bOrderService.selectOrderByUser(order);
    }
    @RequestMapping("confirm")
    public String confirm(@RequestBody Order order){
        order = bOrderService.selectOrderById(order);
        //border ----> finish
        bOrderService.confirm(order);
        //order ----> buyer and finish
        orderService.confirm(order);
        //user -------> count+
        User user = new User();
        user.setUsername(order.getSeller());
        User seller = userService.selectUserByUsername(user);
        seller.setSuccess(String.valueOf(Integer.parseInt(seller.getSuccess()) + 1));
        user.setUsername(order.getBuyer());
        User buyer = userService.selectUserByUsername(user);
        buyer.setSuccess(String.valueOf(Integer.parseInt(seller.getSuccess()) + 1));
        userService.confirm(buyer);
        userService.confirm(seller);
        //friend -----> count+
        Friend friend = new Friend();
        friend.setFriendname(buyer.getUsername());
        friend.setSuccess(buyer.getSuccess());
        friendService.confirm(friend);
        friend.setFriendname(seller.getUsername());
        friend.setSuccess(seller.getSuccess());
        friendService.confirm(friend);
        //goods -----> delete goods;
        Goods goods = new Goods();
        goods.setId(Integer.valueOf(order.getBrandId()));
        goodsService.deleteById(goods);

        return "success";
    }

    @RequestMapping("cancel")
    public String cancel(@RequestBody Order order){
        order = bOrderService.selectOrderById(order);
        //border ----> fail
        bOrderService.cancel(order);
        //user -------> count+
        User user = new User();
        user.setUsername(order.getSeller());
        User seller = userService.selectUserByUsername(user);
        seller.setSuccess(String.valueOf(Integer.parseInt(seller.getFail()) + 1));
        user.setUsername(order.getBuyer());
        User buyer = userService.selectUserByUsername(user);
        buyer.setSuccess(String.valueOf(Integer.parseInt(seller.getFail()) + 1));
        //confirm 成功失败都记录
        userService.confirm(buyer);
        userService.confirm(seller);
        //friend -----> count+
        Friend friend = new Friend();
        friend.setFriendname(buyer.getUsername());
        friend.setFail(buyer.getFail());
        friendService.cancel(friend);
        friend.setFriendname(seller.getUsername());
        friend.setSuccess(seller.getFail());
        friendService.cancel(friend);
        //goods -----> status==true;
        Goods goods = new Goods();
        goods.setId(Integer.valueOf(order.getBrandId()));
        goodsService.cancel(goods);

        return "success";
    }

}
