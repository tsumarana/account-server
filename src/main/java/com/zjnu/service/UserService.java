package com.zjnu.service;

import com.zjnu.pojo.PageBean;
import com.zjnu.pojo.User;

public interface UserService {
    User selectUser(User user);
    User selectUserByUserInfo(User user);
    void insertUser(User user);
    void deleteByID(User user);
    PageBean<User> selectUserByPage(User user,int currentPage, int pageSize);
    User selectUserById(User user);
    User selectUserByUsername(User user);
    void alterUserInfo(User user);
    void addToken(User user);
    User selectTokenByUsername(User user);
    void cleanToken(User user);
    void logoffUser(User user);
    void freezeUser(User user);
}
