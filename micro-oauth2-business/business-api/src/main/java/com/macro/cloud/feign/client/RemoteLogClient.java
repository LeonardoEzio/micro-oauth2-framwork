package com.macro.cloud.feign.client;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.constant.FeignClientName;
import com.macro.cloud.feign.entity.LogInfo;
import com.macro.cloud.feign.factory.RemoteLogFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:18
 */
@FeignClient(contextId = "log", value = FeignClientName.BUSINESS_SERVER , fallbackFactory = RemoteLogFallBackFactory.class)
public interface RemoteLogClient {

    @PostMapping("/api/log/save")
    CommonResult<Boolean> saveLog(@RequestBody LogInfo logInfo);

}
