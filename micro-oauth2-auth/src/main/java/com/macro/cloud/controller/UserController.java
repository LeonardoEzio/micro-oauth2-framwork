package com.macro.cloud.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.UserInfoClient;
import com.macro.cloud.domain.security.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 15:42
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserInfoClient userInfoClient;

    @PostMapping("find")
    public CommonResult<UserInfo> findUser(){
        CommonResult<UserInfo> userByName = userInfoClient.getUserByName("LeonardoEzio");
        return userByName;
    }
}
