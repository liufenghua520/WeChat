package com.qf.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qf.group.ChannelGroup;
import com.qf.msg.ConnMsg;
import com.qf.msg.HeartMsg;
import com.qf.msg.Msg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 消息的处理器
 *
 * @version 1.0
 * @user ken
 * @date 2019/8/6 11:08
 */
public class WebSocketMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端连接了服务器");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("有一个客户单断开了服务器连接！");
        //移除绑定的关系
        ChannelGroup.removeByChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame text) throws Exception {
        //获得客户端发送过来的消息
        String content = text.text();

        //将消息转换成json {key:value, key2:value2.。。}
        JSONObject jsonObject = JSON.parseObject(content);
        int type = jsonObject.getInteger("type");


        Msg msg = null;
        switch (type){
            case 1:
                //连接消息
                msg = JSON.parseObject(content, ConnMsg.class);
                break;
            case 2:
                //心跳消息
                msg = JSON.parseObject(content, HeartMsg.class);
                break;
        }

        //将当前的msg对象透传到后面的ChannelHandler
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端发生异常！");
        //移除绑定的关系
        ChannelGroup.removeByChannel(ctx.channel());
    }
}
