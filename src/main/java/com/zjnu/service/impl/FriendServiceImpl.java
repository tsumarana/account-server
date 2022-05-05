package com.zjnu.service.impl;

import com.zjnu.mapper.FriendMapper;
import com.zjnu.mapper.UserMapper;
import com.zjnu.pojo.Friend;
import com.zjnu.pojo.User;
import com.zjnu.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper mapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User addFriend(User user) {
        User user1 = userMapper.selectByUsername(user);
        return user1;
    }

    @Override
    public List<Friend> selectAllByUser(Friend friend) {
        List<Friend> friends = mapper.selectAllByUser(friend);
        return friends;
    }

    @Override
    public void addFriend(Friend friend) {
        mapper.addFriend(friend);
    }

    @Override
    public void deleteFriendByUsername(String username) {
        mapper.deleteFriendByUsername(username);
    }

    @Override
    public void freezeUser(String username) {
        mapper.freezeUser(username);
    }

    @Override
    public void alterFriendInfo(Friend friend) {
        mapper.alterFriendInfo(friend);
    }

    @Override
    public boolean isExist(Friend friend) {
        if(mapper.isExist(friend) != null){
            return false;
        }
        return true;
    }

    @Override
    public void confirm(Friend friend) {
        mapper.confirm(friend);
    }

    @Override
    public void cancel(Friend friend) {
        mapper.cancel(friend);
    }
}
