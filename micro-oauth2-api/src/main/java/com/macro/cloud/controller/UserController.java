package com.macro.cloud.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.domain.UserDTO;
import com.macro.cloud.holder.LoginUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取登录用户信息接口
 * Created by macro on 2020/6/19.
 */
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private LoginUserHolder loginUserHolder;

    @GetMapping("/currentUser")
    public UserDTO currentUser() {
        return loginUserHolder.getCurrentUser();
    }

    @GetMapping("/getUser")
    public CommonResult<UserDTO> getUser(@RequestParam("name") String name){
        UserDTO userDTO = new UserDTO(2L,String.format("user %s from api",name),"12345",null,null);
        return CommonResult.success(userDTO);
    }

}
