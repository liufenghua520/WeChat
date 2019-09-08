package com.qf.service;

import com.qf.entity.FriendRequest;

import java.util.List;

public interface IFriendRequestService {

    int insertRequest(FriendRequest friendRequest);

    List<FriendRequest> queryRequestList(Integer uid);

    int requestHandler(FriendRequest request);
}
