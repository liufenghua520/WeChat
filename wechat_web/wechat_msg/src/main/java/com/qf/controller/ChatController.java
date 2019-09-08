package com.qf.controller;

import com.qf.api.IUserService;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.msg.ChatMsg;
import com.qf.service.IChatService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 16:01
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public ResultData sendMsg(ChatMsg chatMsg){

        //将消息入库
        chatService.saveChatMsg(chatMsg);

        //将消息广播给netty服务器，让netty服务器将这个消息发送给指定的客户端
        User user = userService.queryById(chatMsg.getToid());
        String deviceId = (String) redisTemplate.opsForValue().get(user.getUsername());
        chatMsg.setDeviceId(deviceId);

        //通过rabbitmq将这个消息广播给所有的netty集群
        rabbitTemplate.convertAndSend("netty_exchange", "", chatMsg);

        return ResultData.createSuccResult(null);
    }
}
