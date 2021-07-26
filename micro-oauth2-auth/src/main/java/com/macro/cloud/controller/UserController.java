package com.macro.cloud.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.UserInfoClient;
import com.macro.cloud.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:47
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserInfoClient userInfoClient;

    @RequestMapping("find")
    public CommonResult<UserDTO> findUser(String name) throws Exception{
        CommonResult<UserDTO> userByName = userInfoClient.getUserByName(name);
        if (name.equals("aaaa")){
            throw new OAuth2Exception("aaaa is not name");
        }
        return userByName;
    }
}
