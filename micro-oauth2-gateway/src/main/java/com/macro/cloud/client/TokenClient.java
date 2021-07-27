package com.macro.cloud.client;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.fallback.TokenClientImpl;
import com.macro.cloud.domain.security.Oauth2TokenInfo;
import com.macro.cloud.domain.security.TokenVerifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:40
 */
@FeignClient(name = "micro-oauth2-auth",fallback = TokenClientImpl.class)
public interface TokenClient {

    @PostMapping("/oauth/verify")
    CommonResult<Oauth2TokenInfo> verifyAccessToken(@RequestBody TokenVerifyRequest tokenVerifyRequest);
}
