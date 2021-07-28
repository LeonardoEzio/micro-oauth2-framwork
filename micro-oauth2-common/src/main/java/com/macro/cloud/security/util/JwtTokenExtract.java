package com.macro.cloud.security.util;

import cn.hutool.json.JSONUtil;
import com.macro.cloud.security.constant.SecurityConstant;
import com.macro.cloud.security.entity.UserToken;
import com.nimbusds.jose.JWSObject;

import java.text.ParseException;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 11:16
 *
 * jwt token 提取工具类
 *
 */
public class JwtTokenExtract {

    public static UserToken extractToken(String realToken) {
        //提取Token信息
        UserToken userToken = null;
        realToken = realToken.contains(SecurityConstant.TOKEN_TYPE) ? realToken.replace(SecurityConstant.TOKEN_TYPE, "") : realToken;
        try {
            JWSObject jwsToken = JWSObject.parse(realToken);
            String tokenStr = JSONUtil.toJsonStr(jwsToken.getPayload().toJSONObject());
            userToken = JSONUtil.toBean(tokenStr, UserToken.class);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userToken;
    }
}
