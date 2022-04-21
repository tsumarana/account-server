package com.zjnu.service;

import com.zjnu.pojo.LoginBean;
import com.zjnu.pojo.PageBean;
import com.zjnu.pojo.User;

public interface UserService {
    LoginBean selectUser(User user,String id);
    User selectUserByUserInfo(User user);
    String insertUser(User user, String id);
    void deleteByID(User user);
    PageBean<User> selectUserByPage(User user,int currentPage, int pageSize);
    User selectUserById(User user);
    User selectUserByUsername(User user);
    void alterUserInfo(User user);
    void addToken(User user);
    User selectTokenByUsername(User user);
    String cleanToken(User user);
    void logoffUser(User user);
    void freezeUser(User user);
    void confirm(User user);
}
