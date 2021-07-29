package com.macro.cloud.web.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.security.entity.UserInfo;
import com.macro.cloud.security.entity.UserToken;
import cn.hutool.core.bean.BeanUtil;
import com.macro.cloud.dao.entity.SysUser;
import com.macro.cloud.holder.LoginUserHolder;
import com.macro.cloud.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 10:48
 */
@RestController
@RequestMapping("user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private LoginUserHolder loginUserHolder;

    @PostMapping("add")
    public CommonResult<SysUser> addUser(@RequestBody SysUser sysUser){
        SysUser result = userInfoService.addUser(sysUser);
        return CommonResult.success(result);
    }

    @GetMapping("currentUser")
    public UserToken currentUser() {
        return loginUserHolder.getCurrentUser();
    }

    @GetMapping("getByName")
    public CommonResult<UserInfo> findUserByName(@RequestParam("name") String name){
        SysUser sysUser = userInfoService.findUserByName(name);
        if (sysUser != null){
            UserInfo userInfo = BeanUtil.copyProperties(sysUser, UserInfo.class);
            userInfo.setRoles(Arrays.asList("admin","sysadmin"));
//            userInfo.setMenus(Arrays.asList("/api/user/add"));
            List<String> menus = new ArrayList<>();
            menus.add("/api/user/add");
            for (int i = 0 ; i < 9999 ; i++){
                menus.add(String.format("/api/user/%s", i));
            }
            //"/api/user/disable"
            userInfo.setMenus(menus);
            return CommonResult.success(userInfo);
        }
        return CommonResult.failed();
    }

    @PostMapping("disable")
    public CommonResult<Boolean> disableUser(@RequestBody SysUser sysUser){
        boolean result = userInfoService.disableUser(sysUser);
        return CommonResult.success(result);
    }

    @PostMapping("delete")
    public CommonResult<Boolean> deleteUser(@RequestBody SysUser sysUser){
        boolean result = userInfoService.deleteUser(sysUser);
        return CommonResult.success(result);
    }
}
