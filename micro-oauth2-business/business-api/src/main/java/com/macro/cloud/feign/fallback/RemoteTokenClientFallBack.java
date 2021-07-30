package com.macro.cloud.feign.fallback;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.request.TokenVerifyRequest;
import com.macro.cloud.feign.entity.Oauth2TokenInfo;
import com.macro.cloud.feign.client.RemoteTokenClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:41
 */
@Slf4j
@Component
public class RemoteTokenClientFallBack implements RemoteTokenClient {

    @Setter
    private Throwable cause;

    @Override
    public CommonResult<Oauth2TokenInfo> verifyAccessToken(TokenVerifyRequest tokenVerifyRequest) {
        return CommonResult.failed(cause.getMessage());
    }
}
