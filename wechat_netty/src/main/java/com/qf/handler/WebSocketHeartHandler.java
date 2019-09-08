package com.qf.handler;

import com.qf.msg.HeartMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 11:45
 */
public class WebSocketHeartHandler extends SimpleChannelInboundHandler<HeartMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartMsg heartMsg) throws Exception {
        //收到一个心跳消息
        ctx.writeAndFlush(new TextWebSocketFrame("heart"));
    }
}
