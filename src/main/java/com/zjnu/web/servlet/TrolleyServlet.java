package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Goods;
import com.zjnu.pojo.Trolley;
import com.zjnu.service.TrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/trolley")
public class TrolleyServlet {
    @Autowired
    TrolleyService trolleyService;
    @RequestMapping("/selectTrolley")
   public void selectTrolley(HttpServletRequest req,HttpServletResponse resp) throws IOException{
       req.setCharacterEncoding("utf-8");
       String s = req.getReader().readLine();
       Trolley trolley = JSON.parseObject(s, Trolley.class);
       List<Trolley> trolleys = trolleyService.selectTrolley(trolley);
       resp.setContentType("text/json;charset=utf-8");
       String jsonString = JSON.toJSONString(trolleys);
       resp.getWriter().write(jsonString);
   }
   //删除
   @RequestMapping("/deleteTrolley")
   public void deleteTrolley(HttpServletRequest req ,HttpServletResponse resp) throws IOException{
       req.setCharacterEncoding("utf-8");
       String s = req.getReader().readLine();
       Trolley trolley = JSON.parseObject(s, Trolley.class);
       trolleyService.deleteTrolley(trolley);
   }
   //保存数量
   @RequestMapping("/saveCount")
   public void saveCount(HttpServletRequest req,HttpServletResponse resp) throws IOException{
       req.setCharacterEncoding("utf-8");
       String s = req.getReader().readLine();
       List<Trolley> trolleys = JSON.parseArray(s, Trolley.class);
       System.out.println(trolleys);
       trolleys.size();
       for(Trolley trolley : trolleys){
            trolleyService.saveCount(trolley);
       }
   }
   //添加到购物车
   @RequestMapping("/addTrolley")
   public void addTrolley(HttpServletRequest req,HttpServletResponse resp) throws IOException{
       System.out.println("添加商品");
       req.setCharacterEncoding("utf-8");
       String s = req.getReader().readLine();
       System.out.println(s);
       Goods goods = JSON.parseObject(s, Goods.class);
       Trolley trolley = new Trolley();
       System.out.println(goods);
       trolley.setPrice(String.valueOf(goods.getPrice()));
       trolley.setBrandId(goods.getId());
       trolley.setSeller(goods.getSeller());
       trolley.setName(goods.getTitle());
       trolley.setUsername(goods.getUsername());
       trolleyService.addTrolley(trolley);
   }

}
