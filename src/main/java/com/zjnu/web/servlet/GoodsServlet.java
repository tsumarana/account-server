package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Goods;
import com.zjnu.pojo.Order;
import com.zjnu.pojo.PageBean;
import com.zjnu.service.GoodsService;
import com.zjnu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/goods")
public class GoodsServlet {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd");
    //查看信息
    @RequestMapping("/selectInfo")
    public void selectInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        int id = Integer.parseInt(reader.readLine());
        Goods goods = goodsService.selectInfo(id);
        String jsonString = JSON.toJSONString(goods);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    //分页查询
    @RequestMapping("/selectByPage")
    public void selectByPage(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        PageBean<Goods> goodsPageBean = goodsService.selectByPage(currentPage, pageSize);
        //转为JSON
        String jsonString = JSON.toJSONString(goodsPageBean);
        //写数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    //条件查询
    @RequestMapping("selectGoods")
    public  List<Goods> selectGoods(@RequestBody Goods goods, HttpServletResponse resp) throws IOException {
        List<Goods> goods1 = goodsService.selectGoods(goods);
        return goods1;
    }
    //按条件分页查询
    @RequestMapping("/selectByPageAndCondition")
    public void selectByPageAndCondition(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        String params = reader.readLine();
        Goods goods = JSON.parseObject(params, Goods.class);
        PageBean<Goods> goodsPageBean = goodsService.selectByPageAndCondition(currentPage, pageSize, goods);
        String jsonString = JSON.toJSONString(goodsPageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    //通过id查找
    @RequestMapping("/selectById")
    public void selectById(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        System.out.println("进入selectByid");
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        Goods goods1 = JSON.parseObject(s, Goods.class);
        System.out.println(goods1.getId());
        Goods goods = goodsService.selectById(goods1.getId());
        resp.setContentType("text/json;charset=utf-8");
        String jsonString = JSON.toJSONString(goods);
        resp.getWriter().write(jsonString);
    }
    //新增商品
    @RequestMapping("/addGoods")
    public void addGoods(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("utf-8");
        String readLine = req.getReader().readLine();
        Goods goods = JSON.parseObject(readLine, Goods.class);
        String reId= String.valueOf(UUID.randomUUID());
        goods.setRe_id(reId);
        goodsService.addGoods(goods);
        Goods goods1 = goodsService.selectByReId(goods);
        Order order = new Order();
        order.setBrandId(String.valueOf(goods1.getId()));
        order.setSeller(goods1.getSeller());
        order.setName(goods1.getTitle());
        order.setPrice(String.valueOf(goods1.getPrice()));
        order.setTime(sdf1.format(new Date()));
        orderService.addOrder(order);
        resp.getWriter().write("success");
    }
    //通过id删除商品
    @RequestMapping("/deleteById")
    public void deleteById(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("utf-8");
        String readLine = req.getReader().readLine();
        Goods goods = JSON.parseObject(readLine, Goods.class);
        goodsService.deleteById(goods);
        resp.getWriter().write("success");
    }
    //上传商品图片
    @RequestMapping("/upload")
    public Map<String,Object> fileupload(MultipartFile file, HttpServletRequest req){
        Map<String,Object> result = new HashMap<>();
        String originName = file.getOriginalFilename();
        String s = originName;
        String s1 = s.split("\\.")[1];
        if(!originName.endsWith("jpeg")&&!originName.endsWith("jpg")&&!originName.endsWith("png")){
            result.put("status","error");
            result.put("msg","文件类型不对");
            return result;
        }
        String format = sdf.format(new Date());

        String realPath = "C:\\Program Files\\Nginx\\nginx-1.20.2\\html\\data\\brand"+format;
        File folder = new File(realPath);
        if(!folder.exists()){
            folder.mkdir();
        }
        String newName = UUID.randomUUID().toString() +'.'+ s1;
        try {
            file.transferTo(new File(folder,newName));
            result.put("status","success");
            String url = "http://localhost/data/brand"+format+'/'+newName;
            result.put("url",url);
        } catch (IOException e) {
            result.put("status","error");
            result.put("msg","error");
        }

        return result;
    }
}
