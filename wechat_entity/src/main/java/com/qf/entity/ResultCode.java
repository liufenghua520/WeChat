package com.qf.entity;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/30 10:37
 */
public interface ResultCode {

    Integer RESULT_SUCC = 1000;
    Integer SERVER_EXCEPTION = 1100;//服务异常

    //用户相关的返回码
    Integer USERNAME_ISEXTRA = 2001;//用户名已经存在
    Integer USERNAME_ERROR = 2002;//用户名错误
    Integer PASSWORD_ERROR = 2003;//密码错误
    Integer USER_NO = 2004;//密码错误

    //申请相关的返回码
    Integer IS_FRIEND = 3001;//已经是好友关系
    Integer SEND_HAVING = 3002;//已经发送过添加的申请
}
