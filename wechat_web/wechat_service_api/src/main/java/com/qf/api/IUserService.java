package com.qf.api;

import com.qf.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("WEB-USER")
public interface IUserService {

    @RequestMapping("/user/queryById")
    User queryById(@RequestParam("uid") Integer uid);
}
