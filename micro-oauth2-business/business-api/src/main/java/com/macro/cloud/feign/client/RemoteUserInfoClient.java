package com.macro.cloud.feign.client;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.constant.FeignClientName;
import com.macro.cloud.feign.entity.UserInfo;
import com.macro.cloud.feign.factory.RemoteUserInfoFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:40
 */
@FeignClient(contextId = "user" ,value = FeignClientName.BUSINESS_SERVER,fallback = RemoteUserInfoFallBackFactory.class)
public interface RemoteUserInfoClient {

    @GetMapping("/api/user/getByName")
    CommonResult<UserInfo> getUserByName(@RequestParam(value = "name") String name);
}
