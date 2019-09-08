package com.qf.controller;

import com.qf.entity.ResultCode;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.msg.ShutDownMsg;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/30 10:09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    
    @RequestMapping("/register")
    public ResultData<String> register(User user){

        int result = userService.registerUser(user);

        ResultData<String> resultData = null;
        if(result > 0){
            //成功
            resultData = ResultData.createSuccResult(null);
        } else if(result == -1){
            //用户名已经被査勇
            resultData = ResultData.createFailResult(ResultCode.USERNAME_ISEXTRA, "用户名已经存在！", null);
        } else {
            //其他的错误
            resultData = ResultData.createFailResult(ResultCode.SERVER_EXCEPTION, "服务端异常！", null);
        }

        return resultData;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public ResultData<User> login(String username, String password, String deviceId){
        //根据用户名查询用户信息
        User user = userService.queryByUsername(username);

        if(user == null){
            //用户名错误
            return ResultData.createFailResult(ResultCode.USERNAME_ERROR, "用户名不存在！", null);
        } else if(!user.getPassword().equals(password)){
            //密码错误
            return ResultData.createFailResult(ResultCode.PASSWORD_ERROR, "密码错误！", null);
        }

        //登录成功
        user.setPassword(null);

        //将当前的账号和对应的设备信息保存到redis中
        String oldDeviceId = (String) redisTemplate.opsForValue().get(username);
        if(oldDeviceId != deviceId){
            //更新当前的设备号
            redisTemplate.opsForValue().set(username, deviceId);

            if(oldDeviceId != null) {
                //广播消息，让旧的设备下线
                ShutDownMsg shutDownMsg = new ShutDownMsg();
                shutDownMsg.setDeviceId(oldDeviceId);

                //广播消息
                rabbitTemplate.convertAndSend("netty_exchange", "", shutDownMsg);
            }
        }
        return ResultData.createSuccResult(user);
    }

    /**
     * 搜索用户
     * @return
     */
    @RequestMapping("/search")
    public ResultData<User> searchByUsername(String username){

        User user = userService.queryByUsername(username);
        if(user != null){
            return ResultData.createSuccResult(user);
        }

        return ResultData.createFailResult(ResultCode.USER_NO, "用户不存在", null);
    }

    /**
     * 其他微服务调用的接口
     * @param uid
     * @return
     */
    @RequestMapping("/queryById")
    public User queryById(Integer uid){
        return userService.queryById(uid);
    }
}
