package com.qf.msg;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 10:36
 */
@Data
public class Msg implements Serializable {

    /**
     * 消息的类型：
     * 1 - 初始化连接消息
     * 2 - 心跳消息
     * 3 - 下线消息
     * 4 - 正在输入...
     * 5 - 停止输入...
     * 6 - 单聊消息
     * 7 - 群聊消息
     *
     */
    @TableField(exist = false)
    public int type;
    //需要下线的设备号
    @TableField(exist = false)
    public String deviceId;

    public Msg(int type){
        this.type = type;
    }

}
