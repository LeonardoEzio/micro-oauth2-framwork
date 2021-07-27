package com.macro.cloud.service;

import com.macro.cloud.dao.entity.SysUser;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 11:28
 */
public interface UserInfoService {

    SysUser addUser(SysUser user);

    SysUser findUserByName(String userName);

    boolean disableUser(SysUser user);

    boolean deleteUser(SysUser user);

}
