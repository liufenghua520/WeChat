package com.qf.handler;

import com.alibaba.fastjson.JSON;
import com.qf.msg.Msg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 14:31
 */
public class WebSocketOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        if(msg instanceof Msg){
            String json = JSON.toJSONString(msg);
            TextWebSocketFrame frame = new TextWebSocketFrame(json);
            super.write(ctx, frame, promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }
}
