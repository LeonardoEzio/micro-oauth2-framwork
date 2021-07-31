package leonardo.ezio.personal.security.util;

import leonardo.ezio.personal.security.constant.SecurityConstant;
import leonardo.ezio.personal.feign.entity.UserToken;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import org.apache.commons.lang3.StringUtils;

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
            if (StringUtils.isNotEmpty(realToken)){
                JWSObject jwsToken = JWSObject.parse(realToken);
                String tokenStr = JSONUtil.toJsonStr(jwsToken.getPayload().toJSONObject());
                userToken = JSONUtil.toBean(tokenStr, UserToken.class);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userToken;
    }
}
