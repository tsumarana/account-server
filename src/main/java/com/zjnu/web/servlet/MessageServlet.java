package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Message;
import com.zjnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/message")
public class MessageServlet {
    @Autowired
    MessageService messageService;
    //获取消息列表
    @RequestMapping("/selectMessage")
    public void selectMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        Message message = JSON.parseObject(s, Message.class);
        List<Message> messages = messageService.selectMessage(message);
        resp.setContentType("text/json;charset=utf-8");
        String jsonString = JSON.toJSONString(messages);
        resp.getWriter().write(jsonString);
    }
    //加入消息
    @RequestMapping("addMessage")
    public void addMessage(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        Message message = JSON.parseObject(s, Message.class);
        messageService.addMessage(message);
    }
}
