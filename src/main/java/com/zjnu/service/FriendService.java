package com.zjnu.service;

import com.zjnu.pojo.Friend;
import com.zjnu.pojo.User;

import java.util.List;

public interface FriendService {
    public User addFriend(User user);
    public List<Friend> selectAllByUser(Friend friend);
    public  void  addFriend(Friend friend);
    void deleteFriendByUsername(String username);
    void freezeUser(String username);
    void alterFriendInfo(Friend friend);
    boolean isExist(Friend friend);
    void confirm(Friend friend);
    void cancel(Friend friend);
}
