package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjnu.KeyConfig.Key;
import com.zjnu.pojo.Friend;
import com.zjnu.pojo.LoginBean;
import com.zjnu.pojo.PageBean;
import com.zjnu.pojo.User;
import com.zjnu.service.*;
import com.zjnu.util.GenerateToken;
import org.apache.axis.encoding.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import java.util.concurrent.TimeUnit;

import static com.zjnu.KeyConfig.Key.Token_Key;

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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //校验用户名密码生成token
    @PostMapping("/selectUser")
    public LoginBean selectUser(@RequestBody User user, HttpServletRequest request) throws IOException {
        return userService.selectUser(user,request.getSession().getId());
    }

    //退出登录
    @PostMapping("/exit")
    public String exit(@RequestBody User user) throws IOException{
        return userService.cleanToken(user);
    }

    //注册
    @PostMapping("/selectUserByUserInfo")
    public String selectUserByUserInfo(@RequestBody User user, HttpServletRequest request) throws IOException {
        return userService.insertUser(user, request.getSession().getId());
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
    //TODO 通过用户名查找用户
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
    //TODO 修改用户信息
    @RequestMapping("/alterUserInfo")
    public void alterUserInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        req.setCharacterEncoding("utf-8");
        String s = req.getReader().readLine();
        User user = JSON.parseObject(s, User.class);
        Friend friend = new Friend();
        friend.setImg(user.getImg());
        friend.setFriendname(user.getUsername());
        userService.alterUserInfo(user);
        friendService.alterFriendInfo(friend);
    }
    //TODO 注销
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
    //TODO 冻结用户
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

    //TODO 上传头像
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

        String realPath = "D:\\Nginx\\nginx-1.20.2\\html\\data\\avatar"+format;
        File folder = new File(realPath);
        if(!folder.exists()){
            folder.mkdir();
        }
        String newName = UUID.randomUUID().toString() +"."+ s1;


        try {
            file.transferTo(new File(folder,newName));
            result.put("status","success");
            String url = "http://localhost/data/avatar"+format+'/'+newName;
            result.put("url",url);
        } catch (IOException e) {
            result.put("status","error");
            result.put("msg","error");
        }

        return result;
    }
}
