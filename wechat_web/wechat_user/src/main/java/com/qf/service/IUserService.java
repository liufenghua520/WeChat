package com.qf.service;

import com.qf.entity.User;

public interface IUserService {

    int registerUser(User user);

    User queryByUsername(String username);

    int updateHeader(String header, String headerCrm, Integer uid);

    User queryById(Integer uid);
}
