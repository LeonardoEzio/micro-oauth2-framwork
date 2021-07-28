package com.macro.cloud.client;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.fallback.OauthUrlClientImpl;
import com.macro.cloud.feign.constant.FeignClientName;
import com.macro.cloud.security.entity.OauthUrlValidator;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:52
 */
@FeignClient(name = FeignClientName.BUSINESS_SERVER , fallback = OauthUrlClientImpl.class)
public interface OauthUrlClient {

    @GetMapping("/api/oauthUrl/sync")
    CommonResult<List<OauthUrlValidator>> syncOauthUrl();

}
