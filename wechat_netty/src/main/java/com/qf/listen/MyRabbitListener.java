package com.qf.listen;

import com.qf.group.ChannelGroup;
import com.qf.msg.Msg;
import io.netty.channel.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 14:26
 */
@Component
public class MyRabbitListener {

    @RabbitListener(queues = "netty_queue_${netty.ip}_${netty.port}")
    public void msgHandler(Msg msg){
        System.out.println("接收到RabbitMq的广播消息：" + msg);
        //获得当前需要发送的设备号
        String deviceId = msg.getDeviceId();

        //通过设备号找到Channel
        Channel channel = ChannelGroup.getChannel(deviceId);
        System.out.println("要发送的设备号deviceId:" + deviceId + " - " + channel);

        if(channel != null) {
            channel.writeAndFlush(msg);
        }
    }
}
