package com.qf.msg;

import lombok.Data;

/**
 * 连接消息
 *
 * @version 1.0
 * @user ken
 * @date 2019/8/6 11:39
 */
@Data
public class ConnMsg extends Msg {

    private String username;
    private String deviceid;

    public ConnMsg() {
        super(1);
    }
}
