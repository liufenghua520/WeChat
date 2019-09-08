package com.qf.controller;

import com.qf.entity.Friends;
import com.qf.entity.ResultData;
import com.qf.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/2 10:02
 */
@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private IFriendService friendService;

    /**
     * 查询好友列表
     * @param uid
     * @return
     */
    @RequestMapping("/list")
    public ResultData<List<Friends>> queryFriendList(Integer uid){
        List<Friends> friends = friendService.queryFriends(uid);
        return ResultData.createSuccResult(friends);
    }
}
