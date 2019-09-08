package com.qf.msg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/6 10:39
 */
@Data
public class ChatMsg extends Msg {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fromid;
    private Integer toid;
    private String context;
    private Integer msgtype;//0 - 文本 1 - 图片 2 - 音频
    private Date createtime = new Date();
    private Integer status = 0;//0 - 未读 1 - 已读

    public ChatMsg(){
        super(6);
    }
}
