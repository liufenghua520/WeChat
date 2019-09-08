package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/1 14:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fromid;
    private Integer toid;
    private Date createtime;
    private String content;
    private Integer status = 0;

    @TableField(exist = false)
    private User fromUser;
}
