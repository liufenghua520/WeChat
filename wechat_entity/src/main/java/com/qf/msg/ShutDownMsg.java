package com.qf.msg;

import lombok.Data;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 14:12
 */
@Data
public class ShutDownMsg extends Msg {

    public ShutDownMsg() {
        super(3);
    }
}
