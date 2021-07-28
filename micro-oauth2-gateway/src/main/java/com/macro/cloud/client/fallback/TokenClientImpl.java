package com.macro.cloud.client.fallback;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.TokenClient;
import com.macro.cloud.security.entity.Oauth2TokenInfo;
import com.macro.cloud.feign.request.TokenVerifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:41
 */
@Slf4j
@Service
public class TokenClientImpl implements TokenClient {

    @Override
    public CommonResult<Oauth2TokenInfo> verifyAccessToken(TokenVerifyRequest tokenVerifyRequest) {
        return CommonResult.failed("auth service not available !!! ");
    }
}
