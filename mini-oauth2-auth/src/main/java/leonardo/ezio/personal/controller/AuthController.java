package leonardo.ezio.personal.controller;

import cn.hutool.core.util.StrUtil;
import leonardo.ezio.personal.annotation.LogModule;
import leonardo.ezio.personal.annotation.SysLog;
import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.domain.Oauth2TokenDto;
import leonardo.ezio.personal.enums.LogPlat;
import leonardo.ezio.personal.enums.LogType;
import leonardo.ezio.personal.enums.OperationType;
import leonardo.ezio.personal.feign.request.TokenVerifyRequest;
import leonardo.ezio.personal.security.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 * Created by macro on 2020/7/17.
 */
@RestController
@RequestMapping("/oauth")
@LogModule(name = "授权模块",plat = LogPlat.AUTH)
public class AuthController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * Oauth2登录认证
     */
    @PostMapping(value = "/token")
    @SysLog(name = "获取令牌",type = LogType.LOGGING,operation = OperationType.LOGIN)
    public CommonResult<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(SecurityConstant.TOKEN_TYPE).build();
        return CommonResult.success(oauth2TokenDto);
    }


    @PostMapping(value = "/verify")
    public CommonResult<Oauth2TokenDto> verifyAccessToken(@RequestBody TokenVerifyRequest tokenVerifyRequest){
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(tokenVerifyRequest.getAccessToken());
        if (null == oAuth2AccessToken || StrUtil.isBlank(oAuth2AccessToken.getValue()) || oAuth2AccessToken.isExpired()){
            return CommonResult.failed("token expired !!!");
        }
        return CommonResult.success(null);
    }


    @PostMapping(value = "/test")
    public Map<String,Object> user(){
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", "leonardoezio");
        return userInfo;
    }
}
