package com.qf.group;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 11:54
 */
public class ChannelGroup {

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    //获得当前的客户端连接数量
    public static int size(){
        return channelMap.size();
    }

    //根据设备号找到对应的Channel对象
    public static Channel getChannel(String deviceId){
        return channelMap.get(deviceId);
    }

    //添加一个设备
    public static Channel addDevice(String deviceId, Channel channel){
        return channelMap.put(deviceId, channel);
    }


    //根据设备号移除绑定关系
    public static Channel removeDevice(String deviceId){
        return channelMap.remove(deviceId);
    }

    //根据管道移除绑定关系
    public static Channel removeByChannel(Channel channel){
        for (Map.Entry<String, Channel> entry : channelMap.entrySet()) {
            if(channel == entry.getValue()){
                return removeDevice(entry.getKey());
            }
        }
        return null;
    }
}
