package com.macro.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.macro.cloud.dao.entity.SysUser;
import com.macro.cloud.dao.mapper.UserInfoMapper;
import com.macro.cloud.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 11:28
 *
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public SysUser addUser(SysUser user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userInfoMapper.insert(user);
        return user;
    }

    @Override
    public SysUser findUserByName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName,userName);
        queryWrapper.eq(SysUser::getIsDelete, 0);
        return userInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean disableUser(SysUser user) {
        user.setStatus(1);
        return userInfoMapper.updateById(user) == 1;
    }

    @Override
    public boolean deleteUser(SysUser user) {
        user.setIsDelete(1);
        return userInfoMapper.updateById(user) == 1;
    }
}
