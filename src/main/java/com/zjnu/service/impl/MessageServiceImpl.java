package com.zjnu.service.impl;

import com.zjnu.mapper.MessageMapper;
import com.zjnu.pojo.Message;
import com.zjnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper mapper;
    @Override
    public List<Message> selectMessage(Message message) {

        List<Message> messages = mapper.selectMessage(message);

        return messages;
    }

    @Override
    public void addMessage(Message message) {
        mapper.addMessage(message);
    }

    @Override
    public void deleteMessageByUsername(String username) {
        mapper.deleteMessageByUsername(username);
    }

    @Override
    public void freezeUser(String username) {
        mapper.freezeUser(username);
    }
}
