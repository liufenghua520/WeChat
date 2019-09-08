package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/1 14:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends implements Serializable {

    private Integer uid;
    private Integer fid;
    private Date createtime;
    private String beizhu;
    private Integer status = 0;

    @TableField(exist = false)
    private User friend;
}
