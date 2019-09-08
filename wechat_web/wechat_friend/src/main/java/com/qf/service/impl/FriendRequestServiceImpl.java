package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.api.IUserService;
import com.qf.dao.FriendRequestMapper;
import com.qf.entity.FriendRequest;
import com.qf.entity.User;
import com.qf.service.IFriendRequestService;
import com.qf.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/1 14:18
 */
@Service
public class FriendRequestServiceImpl implements IFriendRequestService {

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Autowired
    private IFriendService friendService;

    @Autowired
    private IUserService userService;

    /**
     * 添加申请
     * @param friendRequest
     * @return -1：已经是好友关系   -2：已经添加过申请
     */
    @Override
    public int insertRequest(FriendRequest friendRequest) {

        //当前from 和 to是不是好友 -
        boolean flag = friendService.isFriend(friendRequest.getFromid(), friendRequest.getToid());
        if(flag){
            //已经是好友
            return -1;
        }

        //当前from 有没有给 to 发送过申请，并且这个申请还是待验证
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fromid", friendRequest.getFromid());
        queryWrapper.eq("toid", friendRequest.getToid());
        FriendRequest request = friendRequestMapper.selectOne(queryWrapper);
        if(request != null && request.getStatus() == 0){
            //已经发送过添加好友的申请，而且没有被处理
            return -2;
        }

        return friendRequestMapper.insert(friendRequest);
    }

    @Override
    public List<FriendRequest> queryRequestList(Integer uid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("toid", uid);
        queryWrapper.orderByDesc("createtime");
        List<FriendRequest> requestsList = friendRequestMapper.selectList(queryWrapper);

        //通过fromid查询申请者的详细用户信息
        for (FriendRequest friendRequest : requestsList) {
            User user = userService.queryById(friendRequest.getFromid());
            friendRequest.setFromUser(user);
        }

        return requestsList;
    }

    @Override
    @Transactional
    public int requestHandler(FriendRequest request) {

        FriendRequest friendRequest = friendRequestMapper.selectById(request.getId());
        friendRequest.setStatus(request.getStatus());

        //修改数据库
        friendRequestMapper.updateById(friendRequest);

        //
        if (request.getStatus() == 1){
            //申请通过
            friendService.insertFriend(friendRequest.getFromid(), friendRequest.getToid());
        }

        return 1;
    }
}
