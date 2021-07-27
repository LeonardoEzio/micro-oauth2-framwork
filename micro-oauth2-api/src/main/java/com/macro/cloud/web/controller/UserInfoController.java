package com.macro.cloud.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.macro.cloud.api.CommonResult;
import com.macro.cloud.dao.entity.SysUser;
import com.macro.cloud.domain.security.UserInfo;
import com.macro.cloud.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 10:48
 */
@RestController
@RequestMapping("user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("add")
    public CommonResult<SysUser> addUser(@RequestBody SysUser sysUser){
        SysUser result = userInfoService.addUser(sysUser);
        return CommonResult.success(result);
    }

    @GetMapping("getByName")
    public CommonResult<UserInfo> findUserByName(@RequestParam("name") String name){
        SysUser sysUser = userInfoService.findUserByName(name);
        if (sysUser != null){
            UserInfo userInfo = BeanUtil.copyProperties(sysUser, UserInfo.class);
            userInfo.setRoles(Arrays.asList("admin","sysadmin"));
            userInfo.setMenus(Arrays.asList("/user/add","/user/disable"));
            return CommonResult.success(userInfo);
        }
        return CommonResult.failed();
    }

    @PostMapping("disable")
    public CommonResult<Boolean> disableUser(@RequestBody SysUser sysUser){
        boolean result = userInfoService.deleteUser(sysUser);
        return CommonResult.success(result);
    }

    @PostMapping("delete")
    public CommonResult<Boolean> deleteUser(@RequestBody SysUser sysUser){
        boolean result = userInfoService.deleteUser(sysUser);
        return CommonResult.success(result);
    }
}
