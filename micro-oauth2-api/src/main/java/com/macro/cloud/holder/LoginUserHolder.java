package com.macro.cloud.holder;

import com.macro.cloud.security.constant.SecurityConstant;
import com.macro.cloud.security.util.JwtTokenExtract;
import com.macro.cloud.security.entity.UserToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息
 * Created by macro on 2020/6/17.
 */
@Component
public class LoginUserHolder {

    public UserToken getCurrentUser(){
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader(SecurityConstant.AUTHORIZATION_HEAD);
        UserToken userToken = JwtTokenExtract.extractToken(token);
        return userToken;
    }
}
