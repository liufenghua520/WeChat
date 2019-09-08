package com.qf.service;

import com.qf.entity.Friends;

import java.util.List;

public interface IFriendService {

    boolean isFriend(Integer fromid, Integer toid);

    int insertFriend(Integer fromid, Integer toid);

    List<Friends> queryFriends(Integer uid);
}
