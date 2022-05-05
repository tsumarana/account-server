package com.zjnu.service.impl;

import com.zjnu.mapper.UserMapper;
import com.zjnu.pojo.Friend;
import com.zjnu.pojo.LoginBean;
import com.zjnu.pojo.PageBean;
import com.zjnu.pojo.User;
import com.zjnu.service.FriendService;
import com.zjnu.service.UserService;
import com.zjnu.util.GenerateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.zjnu.KeyConfig.Key.Token_Key;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FriendService friendService;
    private GenerateToken generateToken = new GenerateToken();
    private String checkCode(User user,String id) {
        String checkCode = user.getCheck();
        id = id+ ":" +checkCode;
        String real_check = stringRedisTemplate.opsForValue().get(id);
        if(!checkCode.equals(real_check)){
            return "checkError";
        }
        return null;
    }
    //登录
    @Override
    public LoginBean selectUser(User user, String id) {
        String checkError = checkCode(user,id);
        LoginBean loginBean = new LoginBean();
        if (checkError != null) {
            loginBean.setStatus("checkError");
            return loginBean;
        };
        loginBean.setRole("1013");
        String password = user.getPassword();
        password = DigestUtils.md5Hex(password).trim();
        user.setPassword(password.trim());
        User user1 = mapper.selectUser(user);
        if(user1 != null ) {
            //生token
            String key  = Token_Key;
            String token = generateToken.generate(id, String.valueOf(user1.getId()),user1.getUsername(), user1.getVip());
            user1.setToken(token);
            key += user1.getId();
            stringRedisTemplate.opsForValue().set(key,token,30, TimeUnit.MINUTES);
            //写数据
            loginBean.setRole("1012");
            if(user1.getVip().trim().equals("1")) {
                loginBean.setRole("1011");
            }
            loginBean.setUsername(user1.getUsername());
            loginBean.setId(user1.getId());
            loginBean.setToken(token);
            loginBean.setImg(user1.getImg());
        }
        return loginBean;
    }

    @Override
    public User selectUserByUserInfo(User user) {
        User user1 = mapper.selectUserByUserInfo(user);
        return user1;
    }

    //注册
    @Override
    public String insertUser(User user,String id) {
        if (checkCode(user,id) != null) {
            return "checkError";
        };
        if(selectUserByUserInfo(user) == null){
            String password = user.getPassword();
            password = DigestUtils.md5Hex(password);
            user.setPassword(password);
            mapper.insertUser(user);
            Friend friend = new Friend();
            friend.setUsername(user.getUsername());
            friend.setImg("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3313909130,2406410525&fm=15&gp=0.jpg");
            friend.setFriendname("admin");
            friend.setSuccess("0");
            friend.setFail("0");
            friendService.addFriend(friend);
            friend.setFriendname(user.getUsername());
            friend.setUsername("admin");
            friendService.addFriend(friend);
            return "success";
        }
        return "fail";
    }

    @Override
    public void deleteByID(User user) {
        mapper.deleteById(user);
    }

    @Override
    public PageBean<User> selectUserByPage(User user,int currentPage, int pageSize) {

        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        String username = user.getUsername();
        if(username != null && username.length() > 0){
            user.setUsername("%"+username+"%");
        }
        String email = user.getEmail();
        if(email != null && email.length() > 0){
            user.setEmail("%"+email+"%");
        }
        String idCard = user.getIdCard();
        if(idCard != null && idCard.length()>0){
            user.setIdCard("%"+idCard+"%");
        }
        String name = user.getName();
        if(name != null && name.length()>0){
            user.setName("%"+name+"%");
        }
        List<User> users = mapper.selectUserByPage(user,begin, size);
        for(User user1 :users){
            if(user1.getStatus().equals("true")){
                user1.setStatus("正常");
            }else {
                user1.setStatus("冻结");
            }
        }
        int count = mapper.selectTotalCount(user.getUsername(),user.getEmail(),user.getIdCard(),user.getName());
        PageBean<User> pageBean= new PageBean<User>();
        pageBean.setTotalCount(count);
        pageBean.setRows(users);
        return pageBean;
    }

    @Override
    public User selectUserById(User user) {
        User user1 = mapper.selectUserById(user);
        return user1;
    }

    @Override
    public User selectUserByUsername(User user) {
        User user1 = mapper.selectByUsername(user);
        return user1;
    }

    @Override
    public void alterUserInfo(User user) {
        mapper.alterUserInfo(user);
    }

    @Override
    public void addToken(User user) {
        mapper.addToken(user);
    }

    @Override
    public User selectTokenByUsername(User user) {
        User user1 = mapper.selectTokenByUsername(user);
        return user1;
    }
    //退出登录
    @Override
    public String cleanToken(User user) {
        String key  = Token_Key+user.getId();
        if (stringRedisTemplate.delete(key)) {
            return "ok";
        }
        return "error";
    }

    @Override
    public void logoffUser(User user) {
        mapper.logoffUser(user);
    }

    @Override
    public void freezeUser(User user) {
        mapper.freezeUser(user);
    }

    @Override
    public void confirm(User user) {
        mapper.confirm(user);
    }


}
