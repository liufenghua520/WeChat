package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.api.IUserService;
import com.qf.dao.FriendMapper;
import com.qf.entity.Friends;
import com.qf.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/8/1 14:21
 */
@Service
public class FriendServiceImpl implements IFriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private IUserService userService;

    @Override
    public boolean isFriend(Integer fromid, Integer toid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", fromid);
        queryWrapper.eq("fid", toid);
        Integer result = friendMapper.selectCount(queryWrapper);

        return result == 1;
    }

    @Override
    public int insertFriend(Integer fromid, Integer toid) {

        Friends friends1 = new Friends(fromid, toid, new Date(), null, 0, null);
        Friends friends2 = new Friends(toid, fromid, new Date(), null, 0, null);

        if(!isFriend(friends1.getUid(), friends1.getFid())){
            //添加关系
            friendMapper.insert(friends1);
        }

        if(!isFriend(friends2.getUid(), friends2.getFid())){
            //添加关系
            friendMapper.insert(friends2);
        }
        return 1;
    }

    @Override
    public List<Friends> queryFriends(Integer uid) {

        //根据用户id查询好友列表
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Friends> friendsList = friendMapper.selectList(queryWrapper);

        if(friendsList == null){
            return null;
        }

        //
        for (Friends friends : friendsList) {
            friends.setFriend(userService.queryById(friends.getFid()));
        }
        return friendsList;
    }
}
