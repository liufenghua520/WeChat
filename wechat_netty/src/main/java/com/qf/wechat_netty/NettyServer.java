package com.qf.wechat_netty;

import com.qf.handler.WebSocketConnHandler;
import com.qf.handler.WebSocketHeartHandler;
import com.qf.handler.WebSocketMsgHandler;
import com.qf.handler.WebSocketOutHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 10:52
 */
@Component
public class NettyServer implements CommandLineRunner {

    private EventLoopGroup master = new NioEventLoopGroup();
    private EventLoopGroup slave = new NioEventLoopGroup();

    @Value("${netty.port}")
    private int port;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        //启动Netty服务
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap
                .group(master, slave)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        //编解码Http请求
                        pipeline.addLast(new HttpServerCodec());
                        //聚合Http请求
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                        //升级Http协议为WebSocket协议，并且处理非数据帧的处理器
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                        //设置超时处理器 - 1分钟没有收到客户端的消息则关闭该客户端
                        pipeline.addLast(new ReadTimeoutHandler(1, TimeUnit.MINUTES));
                        //自定义的ChannelHandler
                        pipeline.addLast(new WebSocketOutHandler());//处理出站的消息

                        pipeline.addLast(new WebSocketMsgHandler());
                        pipeline.addLast(new WebSocketConnHandler(redisTemplate));
                        pipeline.addLast(new WebSocketHeartHandler());
                    }
                });

        //绑定端口
        try {
            bootstrap.bind(port).sync();
            System.out.println("Netty服务器已经启动，端口为：" + port);
        } catch (InterruptedException e) {
            //如果绑定出现问题，则线程池资源回收
            master.shutdownGracefully();
            slave.shutdownGracefully();
        }
    }
}
