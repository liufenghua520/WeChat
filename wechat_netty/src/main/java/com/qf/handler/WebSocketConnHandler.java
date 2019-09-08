package com.qf.handler;

import com.qf.group.ChannelGroup;
import com.qf.msg.ConnMsg;
import com.qf.msg.ShutDownMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 11:45
 */
public class WebSocketConnHandler extends SimpleChannelInboundHandler<ConnMsg> {

    private RedisTemplate redisTemplate;

    public WebSocketConnHandler(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConnMsg connMsg) throws Exception {
        //有一个连接初始化消息
        String username = connMsg.getUsername();
        String deviceid = connMsg.getDeviceid();

        System.out.println("连接的用户：" + username + " 绑定的设备:" + deviceid);
        //设备的绑定
        ChannelGroup.addDevice(deviceid, ctx.channel());

        //判断当前redis中登录的设备号是不是和上线的设备号一致
        String redisDeviceid = (String) redisTemplate.opsForValue().get(username);
        if(redisDeviceid != null && !deviceid.equals(redisDeviceid)){
            //说明当前设备是被挤下线过的
            //直接下线
            ShutDownMsg shutDownMsg = new ShutDownMsg();
            ctx.writeAndFlush(shutDownMsg);
        }

    }
}
