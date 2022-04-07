package com.zjnu.service.impl;

import com.zjnu.mapper.UserMapper;
import com.zjnu.pojo.PageBean;
import com.zjnu.pojo.User;
import com.zjnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Override
    public User selectUser(User user) {
        User user1 = mapper.selectUser(user);
        return user1;
    }

    @Override
    public User selectUserByUserInfo(User user) {
        User user1 = mapper.selectUserByUserInfo(user);
        return user1;
    }

    @Override
    public void insertUser(User user) {
        mapper.insertUser(user);
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

    @Override
    public void cleanToken(User user) {
        mapper.cleanToken(user);

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
