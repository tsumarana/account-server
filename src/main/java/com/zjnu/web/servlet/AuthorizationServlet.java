package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/filter")
public class AuthorizationServlet {

    @RequestMapping("/tokenCheck")
    public void tokenCheck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        User user = JSON.parseObject(s, User.class);
        String response = "username="+user.getUsername()+"\npassword="+user.getPassword();
        System.out.println(response);
        resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(response);
    }

}
