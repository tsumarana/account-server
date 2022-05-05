package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Message;
import com.zjnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/message")
public class MessageServlet {
    @Autowired
    MessageService messageService;
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd HH-mm-ss");
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
    //上传视频
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile file, String BrandId){
//        C:\Users\me\Videos
        Map<String,Object> result = new HashMap<>();
        String originName = file.getOriginalFilename();
        String s = originName;
        String s1 = s.split("\\.")[1];
        if(!originName.endsWith("mp4")&&!originName.endsWith("avi")&&!originName.endsWith("wmv")){
            result.put("status","error");
            result.put("msg","文件类型不对");
            return result;
        }
        String format = sdf.format(new Date());

        String realPath = "D:\\video"+format;

        File folder = new File(realPath);
        if(!folder.exists()){
            folder.mkdir();
        }
        String newName = UUID.randomUUID().toString() +'.'+ s1;
        try {
            file.transferTo(new File(folder,newName));
            result.put("status","success");
        } catch (IOException e) {
            result.put("status",e.getMessage());
        }
        return result;
    }

}
