package com.qf.service.impl;

import com.qf.dao.ChatMapper;
import com.qf.msg.ChatMsg;
import com.qf.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 16:04
 */
@Service
public class ChatServiceImpl implements IChatService {


    @Autowired
    private ChatMapper chatMapper;

    @Override
    public int saveChatMsg(ChatMsg chatMsg) {
        return chatMapper.insert(chatMsg);
    }
}
