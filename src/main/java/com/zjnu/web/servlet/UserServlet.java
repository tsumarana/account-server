package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjnu.pojo.LoginBean;
import com.zjnu.pojo.PageBean;
import com.zjnu.pojo.User;
import com.zjnu.service.*;
import com.zjnu.util.GenerateToken;
import org.apache.axis.encoding.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserServlet {
    @Autowired
    private UserService userService ;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private TrolleyService trolleyService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private MessageService messageService;
    private GenerateToken generateToken = new GenerateToken();
    //校验用户名密码生成token
    @RequestMapping("/selectUser")
    public void selectUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        String string = reader.readLine();
        String checkCode = JSONObject.parseObject(string).getString("checkCode");
        LoginBean loginBean = new LoginBean();
//        HttpSession session = req.getSession();
//        String checkCode_re = (String) session.getAttribute("checkCode");
//        if(!checkCode.trim().equals(checkCode_re.trim())){
//            resp.getWriter().write("checkError");
//            return ;
//        }
        String id = req.getSession().getId();
        User user = JSON.parseObject(string,User.class);
        User user1 = userService.selectUser(user);
        resp.setContentType("text/json;charset=utf-8");
        if(user1 != null ) {
            //生产token
            String generate = generateToken.generate(id, String.valueOf(user1.getId()),user1.getUsername(), user1.getVip());
            user1.setToken(generate);
            userService.addToken(user1);
            //写数据
            if(user1.getVip().trim().equals("1")) {
                loginBean.setRole("1011");

            }
            else{
                loginBean.setRole("1012");
            }
            loginBean.setUsername(user1.getUsername());
            loginBean.setId(user1.getId());
            loginBean.setToken(generate);
            loginBean.setImg(user1.getImg());
        }else{
            loginBean.setRole("1013");
        }
        String s = JSON.toJSONString(loginBean);
        resp.getWriter().write(s);
    }

    @RequestMapping("/exit")
    public void exit(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        User user = JSON.parseObject(s, User.class);
        userService.cleanToken(user);
    }

    @RequestMapping("/selectUserByUserInfo")
    public void selectUserByUserInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        String string = reader.readLine();
        User user = JSON.parseObject(string,User.class);
        String checkCode = JSONObject.parseObject(string).getString("check");
        HttpSession session = req.getSession();
        String checkCode_re = (String) session.getAttribute("checkCode");
        if(checkCode != "" && checkCode_re!="" && checkCode_re!= null && checkCode != null){
            checkCode = checkCode.toUpperCase().trim();
            checkCode_re = checkCode_re.trim();

        }
        boolean flag = checkCode.equals(checkCode_re);
        System.out.println(flag);
//        boolean flag = true;
        if(flag && userService.selectUserByUserInfo(user) == null ){
            userService.insertUser(user);
            resp.getWriter().write("success");
        }
        else {
            resp.getWriter().write("fail");
        }
    }
    //分页查询用户
    @RequestMapping("/selectUserByPage")
    public void selectUserByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        User user = JSON.parseObject(s, User.class);
        PageBean<User> pageBean = userService.selectUserByPage(user,currentPage, pageSize);
        //转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //写数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    //通过id删除
    @RequestMapping("/deleteById")
    public void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        String string = reader.readLine();
        User user = JSON.parseObject(string, User.class);
        userService.deleteByID(user);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write("success");
    }
    //检查缓存
    @RequestMapping("/checkSession")
    public void checkSession(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        resp.setContentType("text/json;charset=utf-8");
        if(user != null ){
            String jsonString = JSON.toJSONString(user);
            resp.getWriter().write(jsonString);
        }
        else {
            resp.getWriter().write("false");
        }
    }
    //清楚缓存
    @RequestMapping("/cleanSession")
    public void cleanSession(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession();
        session.setAttribute("user",null);
    }
    //根据id查找用户
    @RequestMapping("/selectUserById")
    public void selectUserById(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        System.out.println(s);
        User _user = JSON.parseObject(s, User.class);
        User user = userService.selectUserById(_user);
        resp.setContentType("text/json;charset=utf-8");
        String jsonString = JSON.toJSONString(user);
        resp.getWriter().write(jsonString);
    }
    //通过用户名查找用户
    @RequestMapping("/selectUserByUsername")
    public void selectUserByUsername(HttpServletRequest req ,HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        User user = JSON.parseObject(s, User.class);
        User user1 = userService.selectUserByUsername(user);
        String jsonString = JSON.toJSONString(user1);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    //修改用户信息
    @RequestMapping("/alterUserInfo")
    public void alterUserInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        System.out.println("进入修改");
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        System.out.println(s);
        User user = JSON.parseObject(s, User.class);
        userService.alterUserInfo(user);
    }

    @RequestMapping("/logoffUser")
    public void logoffUser(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        System.out.println(s);
        User user = JSON.parseObject(s, User.class);
        userService.logoffUser(user);
        String username = user.getUsername();
        friendService.deleteFriendByUsername(username);
        orderService.deleteOrderByUsername(username);
        trolleyService.deleteTrolleyByUsername(username);
        goodsService.deleteGoodsByUsername(username);
        messageService.deleteMessageByUsername(username);

    }
    //冻结用户
    @RequestMapping("freezeUser")
    public void freezeUser(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        System.out.println(s);
        User user = JSON.parseObject(s, User.class);
        userService.freezeUser(user);
        String username = user.getUsername();
        friendService.freezeUser(username);
        orderService.freezeUser(username);
        trolleyService.freezeUser(username);
        goodsService.freezeUser(username);
        messageService.freezeUser(username);
        
    }

    //上传图片
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd");

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
        String realPath = "D:\\Vue-workspace\\account-store-online\\src\\assets\\images\\avatar"+format;
        System.out.println(realPath);
        File folder = new File(realPath);
        if(!folder.exists()){
            folder.mkdir();
        }
        String newName = UUID.randomUUID().toString() + s1;
        try {
            file.transferTo(new File(folder,newName));
            result.put("status","success");
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + newName;
            result.put("url",url);
        } catch (IOException e) {
            result.put("status","error");
            result.put("msg",e.getMessage());
        }

        return result;
    }
}
