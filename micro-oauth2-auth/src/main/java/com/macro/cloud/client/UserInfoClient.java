package com.macro.cloud.client;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.fallback.UserInfoClientImpl;
import com.macro.cloud.feign.constant.FeignClientName;
import com.macro.cloud.security.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:40
 */
@FeignClient(name = FeignClientName.BUSINESS_SERVER,fallback = UserInfoClientImpl.class)
public interface UserInfoClient {

    @GetMapping("/api/user/getByName")
    CommonResult<UserInfo> getUserByName(@RequestParam(value = "name") String name);
}
