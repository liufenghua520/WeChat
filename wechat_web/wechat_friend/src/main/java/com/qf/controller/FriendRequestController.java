package com.qf.controller;

import com.qf.entity.FriendRequest;
import com.qf.entity.ResultCode;
import com.qf.entity.ResultData;
import com.qf.service.IFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/1 14:16
 */
@RestController
@RequestMapping("/request")
public class FriendRequestController {

    @Autowired
    private IFriendRequestService friendRequestService;
    
    /**
     * 添加一个添加好友的请求
     * @return
     */
    @RequestMapping("/insert")
    public ResultData insertRequest(FriendRequest request){

        int result = friendRequestService.insertRequest(request);
        if(result > 0){
            //申请成功
            return ResultData.createSuccResult(null);
        } else if(result == -1){
            //已经是好友关系
            return ResultData.createFailResult(ResultCode.IS_FRIEND, "已经是好友关系，不用重复添加！", null);
        } else if(result == -2){
            //已经发送过申请
            return ResultData.createFailResult(ResultCode.SEND_HAVING, "已经发送过申请，不要重复发送！", null);
        }

        return ResultData.createFailResult(ResultCode.SERVER_EXCEPTION, "服务器异常！", null);
    }

    /**
     * 查询当前用户的所有好友申请
     * @return
     */
    @RequestMapping("/list")
    public ResultData<List<FriendRequest>> queryList(Integer uid){
        List<FriendRequest> requests = friendRequestService.queryRequestList(uid);
        return ResultData.createSuccResult(requests);
    }

    /**
     * 处理好友申请
     * @return
     */
    @RequestMapping("/handler")
    public ResultData requestHandler(FriendRequest request){
        int result = friendRequestService.requestHandler(request);
        return ResultData.createSuccResult(null);
    }
}
