package com.macro.cloud.feign.client;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.constant.FeignClientName;
import com.macro.cloud.feign.entity.OauthUrlValidator;
import com.macro.cloud.feign.factory.RemoteOauthUrlFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:52
 */
@FeignClient(contextId = "oauth", value = FeignClientName.BUSINESS_SERVER , fallbackFactory = RemoteOauthUrlFallBackFactory.class)
public interface RemoteOauthUrlClient {

    @GetMapping("/api/oauthUrl/sync")
    CommonResult<List<OauthUrlValidator>> syncOauthUrl();

}
