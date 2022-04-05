package com.zjnu.service;

import com.zjnu.pojo.Message;

import java.util.List;

public interface MessageService {
    List<Message> selectMessage(Message message);
    void addMessage(Message message);
    void deleteMessageByUsername(String username);
    void freezeUser(String username);
}
