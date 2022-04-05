package com.zjnu.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjnu.pojo.LoginBean;
import com.zjnu.pojo.User;
import com.zjnu.service.UserService;
import com.zjnu.util.GenerateToken;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    GenerateToken generateToken = new GenerateToken();
    @Autowired
    UserService userService;
    String role;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //检查token
    public LoginBean checkToken(String token){
        LoginBean loginBean = generateToken.verify(token);
        try{
            String verify = loginBean.getUsername().trim();
        if(!verify.equals("")){
            User user = new User();
            user.setUsername(verify);
            user = userService.selectTokenByUsername(user);
            if(user.getToken().equals(token)){
                if(user.getVip().equals("1")){
                    loginBean.setRole("201");

                }else {
                    loginBean.setRole("200");
                }
            }
        }else {
            loginBean.setRole("401");
        }
        }catch (Exception e){
            System.out.println("校验的token伪造");
        }
        return loginBean;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LoginBean loginBean = null;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String s = req.getRequestURI().split("tomcat-demo1")[1];
        String str  =  req.getRequestURI();
        String auth2="{'/user/selectUserByPage':true,'/user/logoffUser':true}";
        //过滤连接websoket的服务的连接
//        if(s.equals("/websocket")){
//            String token = req.getParameter("token");
//            LoginBean loginBean= checkToken(token);
//            if(loginBean.getRole() != "200" && loginBean.getRole() != "201"){
//                return;
//            }
//        }
        System.out.println("调用接口"+s);
        if(s.equals("/goods/selectByPageAndCondition")){
            String id = String.valueOf(req.getParameter("id"));
            if(id!=null && id.equals("1")){
                auth2 = "{'/user/selectUserByPage':true,'/goods/selectByPageAndCondition':true,'/user/logoffUser':true}";
            }
        }
        String auth1 = "{'/message/selectMessage':true,'/friend/selectList':true,'/message/addMessage':true,'/goods/addGoods':true" +
                ",'/user/selectUserByUsername':true,'/friend/makeFriend':true,'/trolley/selectTrolley':true,'/trolley/deleteTrolley':true" +
                ",'/trolley/saveCount':true,'/trolley/addTrolley':true}";
        JSONObject jsonObject1 = JSON.parseObject(auth1);
        JSONObject jsonObject2 = JSON.parseObject(auth2);
        String token = req.getHeader("Authorization");
        if(jsonObject1.get(s) != null){
            if((Boolean)jsonObject1.get(s)){
                loginBean= checkToken(token);
                if(loginBean.getRole() != "200" && loginBean.getRole() != "201"){
                    return;
                }
                System.out.println("普通用户检验通过");
            }
        }else if(jsonObject2.get(s)!=null) {
            if((Boolean) jsonObject2.get(s)){
                loginBean= checkToken(token);
                if(loginBean.getRole() != "201"){
                    return;
                }
            }
        }
        System.out.println("通过");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {

    }
}
